using Brasilb.Models;

namespace Brasilb.ViewModels;

public class MenuDetailsViewModel
{
    public Menu Menu { get; set; } = new();
    public decimal Prix { get; set; }
}
