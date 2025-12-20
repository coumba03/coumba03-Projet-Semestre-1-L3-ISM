using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("burgers")]
public class Burger
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [Column("nom")]
    [MaxLength(100)]
    public string Nom { get; set; } = string.Empty;

    [Column("prix")]
    public decimal Prix { get; set; }

    [Column("image_url")]
    [MaxLength(500)]
    public string? ImageUrl { get; set; }

    [Column("disponible")]
    public bool Disponible { get; set; } = true;

    [Column("archived")]
    public bool Archived { get; set; } = false;
}
