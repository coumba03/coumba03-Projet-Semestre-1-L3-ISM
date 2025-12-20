using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("paiements")]
public class Paiement
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("commande_id")]
    public int? CommandeId { get; set; }

    [Column("montant")]
    public decimal? Montant { get; set; }

    [Column("mode_paiement")]
    [MaxLength(50)]
    public string? ModePaiement { get; set; }

    [Column("date_paiement")]
    public DateTime DatePaiement { get; set; }

    [Column("valide")]
    public bool Valide { get; set; } = false;

    [Column("reference_transaction")]
    [MaxLength(255)]
    public string? ReferenceTransaction { get; set; }

    public Commande? Commande { get; set; }
}
