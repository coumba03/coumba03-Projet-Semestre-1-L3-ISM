namespace brasil.Models
{
    public class Client
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string Prenom { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string Telephone { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty; // Hashed password
        public string? Adresse { get; set; }
        public bool Archived { get; set; } = false;
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
        
        public ICollection<Commande> Commandes { get; set; } = new List<Commande>();
    }
}
