using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Brasilb.Models;

[Table("commandes")]
public class Commande
{
    [Key]
    [Column("id")]
    public int Id { get; set; }

    [Column("client_id")]
    public int? ClientId { get; set; }

    [Column("type_commande")]
    [MaxLength(50)]
    public string? TypeCommande { get; set; }

    [Column("zone_id")]
    public int? ZoneId { get; set; }

    [Column("etat")]
    [MaxLength(50)]
    public string Etat { get; set; } = "NEW";

    [Column("date_commande")]
    public DateTime DateCommande { get; set; }

    [Column("montant_total")]
    public decimal? MontantTotal { get; set; }

    [Column("archived")]
    public bool Archived { get; set; } = false;

    public Client? Client { get; set; }

    public Zone? Zone { get; set; }

    public List<CommandeItem> Items { get; set; } = new();

    public List<Paiement> Paiements { get; set; } = new();
}
