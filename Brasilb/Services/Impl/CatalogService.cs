using Brasilb.Data;
using Brasilb.Models;
using Microsoft.EntityFrameworkCore;

namespace Brasilb.Services.Impl;

public class CatalogService : ICatalogService
{
    private readonly BrasilbDbContext _db;

    public CatalogService(BrasilbDbContext db)
    {
        _db = db;
    }

    public Task<List<Burger>> GetBurgersAsync()
    {
        return _db.Burgers
            .Where(b => b.Disponible && !b.Archived)
            .OrderBy(b => b.Nom)
            .ToListAsync();
    }

    public Task<List<Menu>> GetMenusAsync()
    {
        return _db.Menus
            .Where(m => m.Disponible && !m.Archived)
            .OrderBy(m => m.Nom)
            .ToListAsync();
    }

    public Task<List<Complement>> GetComplementsAsync(string? type = null)
    {
        var query = _db.Complements.Where(c => c.Disponible && !c.Archived);

        if (!string.IsNullOrWhiteSpace(type))
        {
            query = query.Where(c => c.Type == type);
        }

        return query.OrderBy(c => c.Nom).ToListAsync();
    }

    public Task<List<Zone>> GetZonesAsync()
    {
        return _db.Zones
            .Where(z => !z.Archived)
            .OrderBy(z => z.Nom)
            .ToListAsync();
    }

    public Task<Burger?> GetBurgerByIdAsync(int id)
    {
        return _db.Burgers.FirstOrDefaultAsync(b => b.Id == id && !b.Archived);
    }

    public Task<Menu?> GetMenuByIdAsync(int id)
    {
        return _db.Menus.FirstOrDefaultAsync(m => m.Id == id && !m.Archived);
    }

    public async Task<decimal> GetMenuPriceAsync(int menuId)
    {
        decimal total = 0;

        var compositions = await _db.MenuCompositions
            .Include(mc => mc.Burger)
            .Include(mc => mc.Boisson)
            .Include(mc => mc.Frite)
            .Where(mc => mc.MenuId == menuId)
            .ToListAsync();

        foreach (var comp in compositions)
        {
            if (comp.Burger != null) total += comp.Burger.Prix;
            if (comp.Boisson != null) total += comp.Boisson.Prix;
            if (comp.Frite != null) total += comp.Frite.Prix;
        }

        var extraItems = await _db.MenuItems
            .Include(mi => mi.Complement)
            .Where(mi => mi.MenuId == menuId)
            .ToListAsync();

        foreach (var mi in extraItems)
        {
            if (mi.Complement != null) total += mi.Complement.Prix;
        }

        return total;
    }
}
