namespace brasil.Models
{
    public class Livreur
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string Prenom { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string Telephone { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty; // Hashed
        public int? ZoneId { get; set; }
        public bool Archived { get; set; } = false;
        public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
        
        public Zone? Zone { get; set; }
    }
}
