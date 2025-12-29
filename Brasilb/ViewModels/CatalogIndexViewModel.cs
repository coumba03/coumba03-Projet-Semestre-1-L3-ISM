using Brasilb.Models;

namespace Brasilb.ViewModels;

public class CatalogIndexViewModel
{
    public string Tab { get; set; } = "ALL";

    public List<Burger> Burgers { get; set; } = new();
    public List<MenuWithPriceViewModel> Menus { get; set; } = new();
    public List<Complement> Drinks { get; set; } = new();
    public List<Complement> Sides { get; set; } = new();
}
