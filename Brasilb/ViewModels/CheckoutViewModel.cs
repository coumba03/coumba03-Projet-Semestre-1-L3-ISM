using System.ComponentModel.DataAnnotations;

namespace Brasilb.ViewModels;

public class CheckoutViewModel
{
    [Required]
    public string TypeCommande { get; set; } = "DINE_IN";

    public int? ZoneId { get; set; }

    [Required]
    public string ModePaiement { get; set; } = "WAVE";

    public string? ReferenceTransaction { get; set; }
}
