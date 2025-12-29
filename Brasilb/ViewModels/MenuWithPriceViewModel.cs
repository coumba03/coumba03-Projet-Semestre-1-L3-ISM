using Brasilb.Models;

namespace Brasilb.ViewModels;

public class MenuWithPriceViewModel
{
    public Menu Menu { get; set; } = new();
    public decimal Prix { get; set; }
}