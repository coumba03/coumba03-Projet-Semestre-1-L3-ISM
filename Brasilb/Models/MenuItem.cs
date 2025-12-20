using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("menu_items")]
public class MenuItem
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("menu_id")]
    public int? MenuId { get; set; }

    [Column("complement_id")]
    public int? ComplementId { get; set; }

    [Required]
    [Column("type")]
    [MaxLength(100)]
    public string Type { get; set; } = string.Empty;

    public Menu? Menu { get; set; }

    public Complement? Complement { get; set; }
}
