using Brasilb.Models;

namespace Brasilb.ViewModels;

public class BurgerDetailsViewModel
{
    public Burger Burger { get; set; } = new();
    public List<Complement> Complements { get; set; } = new();
}
