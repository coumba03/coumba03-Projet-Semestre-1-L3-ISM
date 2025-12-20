using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("menus")]
public class Menu
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Required]
    [Column("nom")]
    [MaxLength(100)]
    public string Nom { get; set; } = string.Empty;

    [Column("image_url")]
    [MaxLength(500)]
    public string? ImageUrl { get; set; }

    [Column("disponible")]
    public bool Disponible { get; set; } = true;

    [Column("archived")]
    public bool Archived { get; set; } = false;

    public List<MenuComposition> Compositions { get; set; } = new();

    public List<MenuItem> Items { get; set; } = new();
}
