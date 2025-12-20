namespace brasil.Models
{
    public class Burger
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public decimal Prix { get; set; }
        public string? ImageUrl { get; set; }
        public bool Disponible { get; set; } = true;
        public bool Archived { get; set; } = false;
    }
}
