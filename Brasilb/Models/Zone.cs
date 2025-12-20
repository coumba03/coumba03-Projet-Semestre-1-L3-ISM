using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("zones")]
public class Zone
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [Column("nom")]
    [MaxLength(100)]
    public string Nom { get; set; } = string.Empty;

    [Column("quartiers")]
    public string? Quartiers { get; set; }

    [Column("prix_livraison")]
    public decimal PrixLivraison { get; set; }

    [Column("archived")]
    public bool Archived { get; set; } = false;

    [Column("created_at")]
    public DateTime CreatedAt { get; set; }
}
