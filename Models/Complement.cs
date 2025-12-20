namespace brasil.Models
{
    public class Complement
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string Type { get; set; } = "BOISSON"; // BOISSON ou FRITE
        public decimal Prix { get; set; }
        public string? ImageUrl { get; set; }
        public bool Disponible { get; set; } = true;
        public bool Archived { get; set; } = false;
    }
}
