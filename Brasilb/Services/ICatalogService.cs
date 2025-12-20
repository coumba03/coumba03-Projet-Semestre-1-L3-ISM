using Brasilb.Models;

namespace Brasilb.Services;

public interface ICatalogService
{
    Task<List<Burger>> GetBurgersAsync();
    Task<List<Menu>> GetMenusAsync();
    Task<List<Complement>> GetComplementsAsync(string? type = null);
    Task<List<Zone>> GetZonesAsync();

    Task<Burger?> GetBurgerByIdAsync(int id);
    Task<Menu?> GetMenuByIdAsync(int id);

    Task<decimal> GetMenuPriceAsync(int menuId);
}
