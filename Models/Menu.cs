namespace brasil.Models
{
    public class Menu
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string? ImageUrl { get; set; }
        public bool Disponible { get; set; } = true;
        public bool Archived { get; set; } = false;
        
        // Relation avec MenuComposition
        public ICollection<MenuComposition> Compositions { get; set; } = new List<MenuComposition>();
    }
}
