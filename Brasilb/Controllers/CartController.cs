using System.Security.Claims;
using Brasilb.Helpers;
using Brasilb.Services;
using Brasilb.ViewModels;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Brasilb.Controllers;

public class CartController : Controller
{
    private const string CartKey = "CART";

    private readonly ICatalogService _catalogService;
    private readonly IOrderService _orderService;

    public CartController(ICatalogService catalogService, IOrderService orderService)
    {
        _catalogService = catalogService;
        _orderService = orderService;
    }

    [HttpGet]
    public async Task<IActionResult> Index()
    {
        var cart = GetCart();
        ViewData["Checkout"] = new CheckoutViewModel();
        ViewData["Zones"] = await _catalogService.GetZonesAsync();
        return View(cart);
    }

    [HttpPost]
    public async Task<IActionResult> AddBurger(int id, int quantite = 1, int[]? complementIds = null)
    {
        var burger = await _catalogService.GetBurgerByIdAsync(id);
        if (burger == null)
        {
            return NotFound();
        }

        var cart = GetCart();
        AddOrIncrement(cart, "BURGER", burger.Id, burger.Nom, burger.Prix, quantite);

        if (complementIds != null)
        {
            foreach (var cid in complementIds)
            {
                var c = (await _catalogService.GetComplementsAsync(null)).FirstOrDefault(x => x.Id == cid);
                if (c != null)
                {
                    AddOrIncrement(cart, "COMPLEMENT", c.Id, c.Nom, c.Prix, 1);
                }
            }
        }

        SaveCart(cart);
        return RedirectToAction("Index");
    }

    [HttpPost]
    public async Task<IActionResult> AddMenu(int id, int quantite = 1)
    {
        var menu = await _catalogService.GetMenuByIdAsync(id);
        if (menu == null)
        {
            return NotFound();
        }

        var prix = await _catalogService.GetMenuPriceAsync(menu.Id);
        var cart = GetCart();
        AddOrIncrement(cart, "MENU", menu.Id, menu.Nom, prix, quantite);
        SaveCart(cart);

        return RedirectToAction("Index");
    }

    [HttpPost]
    public async Task<IActionResult> AddComplement(int id, int quantite = 1)
    {
        var complement = (await _catalogService.GetComplementsAsync(null)).FirstOrDefault(c => c.Id == id);
        if (complement == null)
        {
            return NotFound();
        }

        var cart = GetCart();
        AddOrIncrement(cart, "COMPLEMENT", complement.Id, complement.Nom, complement.Prix, quantite);
        SaveCart(cart);

        return RedirectToAction("Index");
    }

    [HttpPost]
    public IActionResult Remove(int index)
    {
        var cart = GetCart();
        if (index >= 0 && index < cart.Items.Count)
        {
            cart.Items.RemoveAt(index);
            SaveCart(cart);
        }

        return RedirectToAction("Index");
    }

    [HttpPost]
    public IActionResult Clear()
    {
        SaveCart(new CartViewModel());
        return RedirectToAction("Index");
    }

    [HttpGet]
    public async Task<IActionResult> Checkout()
    {
        var cart = GetCart();
        if (cart.Items.Count == 0)
        {
            return RedirectToAction("Index");
        }
        
        ViewData["Checkout"] = new CheckoutViewModel();
        ViewData["Zones"] = await _catalogService.GetZonesAsync();
        return View(cart);
    }

    [Authorize]
    [HttpPost]
    public async Task<IActionResult> Checkout(CheckoutViewModel vm)
    {
        if (vm.TypeCommande == "DELIVERY" && vm.ZoneId == null)
        {
            ModelState.AddModelError(nameof(vm.ZoneId), "Veuillez choisir la zone de livraison");
        }

        if (!ModelState.IsValid)
        {
            var cart = GetCart();
            ViewData["Checkout"] = vm;
            ViewData["Zones"] = await _catalogService.GetZonesAsync();
            return View("Index", cart);
        }

        var cartVm = GetCart();
        if (cartVm.Items.Count == 0)
        {
            return RedirectToAction("Index");
        }

        var clientIdStr = User.FindFirstValue(ClaimTypes.NameIdentifier);
        if (!int.TryParse(clientIdStr, out var clientId))
        {
            return RedirectToAction("Login", "Account", new { returnUrl = Url.Action("Index", "Cart") });
        }

        var result = await _orderService.CreateOrderAndPayAsync(clientId, vm, cartVm);
        if (!result.Ok)
        {
            ModelState.AddModelError(string.Empty, result.ErrorMessage);
            ViewData["Checkout"] = vm;
            ViewData["Zones"] = await _catalogService.GetZonesAsync();
            return View(cartVm);
        }

        // Vider le panier
        SaveCart(new CartViewModel());

        // Utiliser TempData pour afficher le message après redirection
        TempData["SuccessMessage"] = "Votre commande a été passée avec succès !";
        TempData["OrderId"] = result.CommandeId?.ToString();

        return RedirectToAction("Index");
    }

    private CartViewModel GetCart()
    {
        return HttpContext.Session.GetObject<CartViewModel>(CartKey) ?? new CartViewModel();
    }

    private void SaveCart(CartViewModel cart)
    {
        HttpContext.Session.SetObject(CartKey, cart);
    }

    private static void AddOrIncrement(CartViewModel cart, string typeItem, int itemId, string nom, decimal prix, int quantite)
    {
        var existing = cart.Items.FirstOrDefault(i => i.TypeItem == typeItem && i.ItemId == itemId);
        if (existing == null)
        {
            cart.Items.Add(new CartItemViewModel
            {
                TypeItem = typeItem,
                ItemId = itemId,
                Nom = nom,
                PrixUnitaire = prix,
                Quantite = quantite
            });
        }
        else
        {
            existing.Quantite += quantite;
        }
    }
}
