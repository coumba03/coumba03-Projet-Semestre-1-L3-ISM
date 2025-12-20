namespace brasil.Models
{
    public class Commande
    {
        public int Id { get; set; }
        public int ClientId { get; set; }
        public string? TypeCommande { get; set; } = "BURGER"; // BURGER | MENU
        public string Etat { get; set; } = "NEW"; // NEW, PENDING, IN_PROGRESS, READY, DELIVERED, CANCELLED
        public DateTime DateCommande { get; set; } = DateTime.UtcNow;
        public decimal MontantTotal { get; set; }
        public bool Archived { get; set; } = false;
        
        public Client? Client { get; set; }
        public ICollection<CommandeItem> Items { get; set; } = new List<CommandeItem>();
        public ICollection<Paiement> Paiements { get; set; } = new List<Paiement>();
    }

    public class CommandeItem
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public string? TypeItem { get; set; } = "BURGER"; // BURGER | MENU
        public int? ItemId { get; set; }
        public int Quantite { get; set; } = 1;
        public decimal PrixUnitaire { get; set; }
        public decimal SousTotal { get; set; }
        
        public Commande? Commande { get; set; }
    }
}
