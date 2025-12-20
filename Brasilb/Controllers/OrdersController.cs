using System.Security.Claims;
using Brasilb.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Brasilb.Controllers;

[Authorize]
public class OrdersController : Controller
{
    private readonly IOrderService _orderService;

    public OrdersController(IOrderService orderService)
    {
        _orderService = orderService;
    }

    [HttpGet]
    public async Task<IActionResult> Index()
    {
        var clientIdStr = User.FindFirstValue(ClaimTypes.NameIdentifier);
        if (!int.TryParse(clientIdStr, out var clientId))
        {
            return RedirectToAction("Login", "Account");
        }

        var orders = await _orderService.GetOrdersForClientAsync(clientId);
        return View(orders);
    }
}
