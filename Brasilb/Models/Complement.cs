using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("complements")]
public class Complement
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [Column("nom")]
    [MaxLength(100)]
    public string Nom { get; set; } = string.Empty;

    [Required]
    [Column("type")]
    [MaxLength(20)]
    public string Type { get; set; } = string.Empty;

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
