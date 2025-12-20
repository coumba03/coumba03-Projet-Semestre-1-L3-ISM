namespace brasil.Models
{
    public class MenuComposition
    {
        public int Id { get; set; }
        public int MenuId { get; set; }
        public int? BurgerId { get; set; }
        public int? BoissonId { get; set; }
        public int? FriteId { get; set; }
        
        public Menu? Menu { get; set; }
        public Burger? Burger { get; set; }
        public Complement? Boisson { get; set; }
        public Complement? Frite { get; set; }
    }
}
