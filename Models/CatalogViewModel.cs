namespace brasil.Models
{
    public class CatalogViewModel
    {
        public List<Burger> Burgers { get; set; } = new();
        public List<Menu> Menus { get; set; } = new();
        public string? Filter { get; set; }
    }
}
