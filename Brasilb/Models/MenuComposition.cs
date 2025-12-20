using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("menu_composition")]
public class MenuComposition
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("menu_id")]
    public int? MenuId { get; set; }

    [Column("burger_id")]
    public int? BurgerId { get; set; }

    [Column("boisson_id")]
    public int? BoissonId { get; set; }

    [Column("frite_id")]
    public int? FriteId { get; set; }

    public Menu? Menu { get; set; }

    public Burger? Burger { get; set; }

    public Complement? Boisson { get; set; }

    public Complement? Frite { get; set; }
}
