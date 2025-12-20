namespace brasil.Models
{
    public class Zone
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string? Quartiers { get; set; }
        public decimal PrixLivraison { get; set; }
        public bool Archived { get; set; } = false;
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
        
        public ICollection<Livreur> Livreurs { get; set; } = new List<Livreur>();
    }
}
