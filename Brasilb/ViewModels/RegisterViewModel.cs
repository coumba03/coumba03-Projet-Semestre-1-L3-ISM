using System.ComponentModel.DataAnnotations;

namespace Brasilb.ViewModels;

public class RegisterViewModel
{
    [Required]
    public string Nom { get; set; } = string.Empty;

    [Required]
    public string Prenom { get; set; } = string.Empty;

    [Required]
    [EmailAddress]
    public string Email { get; set; } = string.Empty;

    [Required]
    public string Telephone { get; set; } = string.Empty;

    public string? Adresse { get; set; }

    [Required]
    [DataType(DataType.Password)]
    [StringLength(100, ErrorMessage = "Le {0} doit comporter au moins {2} et au maximum {1} caractères.", MinimumLength = 6)]
    public string Password { get; set; } = string.Empty;

    [DataType(DataType.Password)]
    [Display(Name = "Confirmer le mot de passe")]
    [Compare("Password", ErrorMessage = "Le mot de passe et la confirmation du mot de passe ne correspondent pas.")]
    public string ConfirmPassword { get; set; } = string.Empty;
}
