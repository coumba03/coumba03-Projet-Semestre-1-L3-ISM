using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("clients")]
public class Client
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [Column("nom")]
    [MaxLength(100)]
    public string Nom { get; set; } = string.Empty;

    [Required]
    [Column("prenom")]
    [MaxLength(100)]
    public string Prenom { get; set; } = string.Empty;

    [Required]
    [Column("email")]
    [MaxLength(100)]
    public string Email { get; set; } = string.Empty;

    [Required]
    [Column("telephone")]
    [MaxLength(20)]
    public string Telephone { get; set; } = string.Empty;

    [Required]
    [Column("password")]
    [MaxLength(255)]
    public string Password { get; set; } = string.Empty;

    [Column("adresse")]
    [MaxLength(255)]
    public string? Adresse { get; set; }

    [Column("archived")]
    public bool Archived { get; set; } = false;

    [Column("created_at")]
    public DateTime CreatedAt { get; set; }

    public List<Commande> Commandes { get; set; } = new();
}
