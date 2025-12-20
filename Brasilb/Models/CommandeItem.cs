using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("commande_items")]
public class CommandeItem
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("commande_id")]
    public int? CommandeId { get; set; }

    [Column("type_item")]
    [MaxLength(50)]
    public string? TypeItem { get; set; }

    [Column("item_id")]
    public int? ItemId { get; set; }

    [Column("quantite")]
    public int? Quantite { get; set; }

    [Column("prix_unitaire")]
    public decimal? PrixUnitaire { get; set; }

    [Column("sous_total")]
    public decimal? SousTotal { get; set; }

    public Commande? Commande { get; set; }
}
