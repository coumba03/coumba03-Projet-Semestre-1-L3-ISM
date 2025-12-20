namespace brasil.Models
{
    public class Paiement
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public decimal Montant { get; set; }
        public string ModePaiement { get; set; } = string.Empty; // Wave | OM
        public DateTime DatePaiement { get; set; } = DateTime.UtcNow;
        public bool Valide { get; set; } = false;
        public string? ReferenceTransaction { get; set; }
        
        public Commande? Commande { get; set; }
    }
}
