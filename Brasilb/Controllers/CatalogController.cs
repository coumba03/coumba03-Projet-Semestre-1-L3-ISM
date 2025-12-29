using Brasilb.Services;
using Brasilb.ViewModels;
using Microsoft.AspNetCore.Mvc;

namespace Brasilb.Controllers;

public class CatalogController : Controller
{
    private readonly ICatalogService _catalogService;

    public CatalogController(ICatalogService catalogService)
    {
        _catalogService = catalogService;
    }

    [HttpGet]
    public async Task<IActionResult> Index(string tab = "ALL")
    {
        var vm = new CatalogIndexViewModel { Tab = tab };

        vm.Burgers = await _catalogService.GetBurgersAsync();
        vm.Menus = await _catalogService.GetMenusAsync();
        vm.Drinks = await _catalogService.GetComplementsAsync("BOISSON");
        vm.Sides = await _catalogService.GetComplementsAsync("FRITE");

        return View(vm);
    }

    [HttpGet]
    public async Task<IActionResult> Burger(int id)
    {
        var burger = await _catalogService.GetBurgerByIdAsync(id);
        if (burger == null)
        {
            return NotFound();
        }

        var vm = new BurgerDetailsViewModel
        {
            Burger = burger,
            Complements = await _catalogService.GetComplementsAsync(null)
        };

        return View(vm);
    }

    [HttpGet]
    public async Task<IActionResult> Menu(int id)
    {
        var menu = await _catalogService.GetMenuByIdAsync(id);
        if (menu == null)
        {
            return NotFound();
        }

        var vm = new MenuDetailsViewModel
        {
            Menu = menu,
            Prix = await _catalogService.GetMenuPriceAsync(menu.Id)
        };

        return View(vm);
    }
}
