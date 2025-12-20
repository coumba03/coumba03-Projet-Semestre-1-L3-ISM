namespace Brasilb.ViewModels;

public class CartItemViewModel
{
    public string TypeItem { get; set; } = string.Empty;
    public int ItemId { get; set; }
    public string Nom { get; set; } = string.Empty;
    public string? Description { get; set; }
    public string? ImageUrl { get; set; }
    public decimal PrixUnitaire { get; set; }
    public int Quantite { get; set; }

    public decimal SousTotal => PrixUnitaire * Quantite;
}
