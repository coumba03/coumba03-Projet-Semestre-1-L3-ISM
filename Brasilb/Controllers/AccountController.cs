using System.Security.Claims;
using Brasilb.Models;
using Brasilb.Services;
using Brasilb.ViewModels;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;

namespace Brasilb.Controllers;

public class AccountController : Controller
{
    private readonly IAuthService _authService;

    public AccountController(IAuthService authService)
    {
        _authService = authService;
    }

    [HttpGet]
    public IActionResult Login(string? returnUrl = null)
    {
        return View(new LoginViewModel { ReturnUrl = returnUrl });
    }

    [HttpPost]
    public async Task<IActionResult> Login(LoginViewModel vm)
    {
        if (!ModelState.IsValid)
        {
            return View(vm);
        }

        var client = await _authService.ValidateCredentialsAsync(vm.Email, vm.Password);
        if (client == null)
        {
            ModelState.AddModelError(string.Empty, "Email ou mot de passe incorrect");
            return View(vm);
        }

        var claims = new List<Claim>
        {
            new(ClaimTypes.NameIdentifier, client.Id.ToString()),
            new(ClaimTypes.Name, $"{client.Prenom} {client.Nom}"),
            new(ClaimTypes.Email, client.Email)
        };

        var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
        await HttpContext.SignInAsync(
            CookieAuthenticationDefaults.AuthenticationScheme,
            new ClaimsPrincipal(identity));

        if (!string.IsNullOrWhiteSpace(vm.ReturnUrl) && Url.IsLocalUrl(vm.ReturnUrl))
        {
            return Redirect(vm.ReturnUrl);
        }

        return RedirectToAction("Index", "Catalog");
    }

    [HttpGet]
    public IActionResult Register()
    {
        return View(new RegisterViewModel());
    }

    [HttpPost]
    public async Task<IActionResult> Register(RegisterViewModel vm)
    {
        if (!ModelState.IsValid)
        {
            return View(vm);
        }

        var client = new Client
        {
            Nom = vm.Nom,
            Prenom = vm.Prenom,
            Email = vm.Email,
            Telephone = vm.Telephone,
            Adresse = vm.Adresse,
            Password = vm.Password
        };

        var result = await _authService.RegisterClientAsync(client);
        if (!result.Ok || result.Client == null)
        {
            ModelState.AddModelError(string.Empty, result.ErrorMessage);
            return View(vm);
        }

        var claims = new List<Claim>
        {
            new(ClaimTypes.NameIdentifier, result.Client.Id.ToString()),
            new(ClaimTypes.Name, $"{result.Client.Prenom} {result.Client.Nom}"),
            new(ClaimTypes.Email, result.Client.Email)
        };

        var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
        await HttpContext.SignInAsync(
            CookieAuthenticationDefaults.AuthenticationScheme,
            new ClaimsPrincipal(identity));

        return RedirectToAction("Index", "Catalog");
    }

    [HttpGet]
    public async Task<IActionResult> Logout()
    {
        await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
        return RedirectToAction("Index", "Catalog");
    }
}
