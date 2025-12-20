using Brasilb.Models;
using Microsoft.EntityFrameworkCore;

namespace Brasilb.Data;

public class BrasilbDbContext : DbContext
{
    public BrasilbDbContext(DbContextOptions<BrasilbDbContext> options) : base(options)
    {
    }

    public DbSet<Burger> Burgers => Set<Burger>();
    public DbSet<Complement> Complements => Set<Complement>();
    public DbSet<Menu> Menus => Set<Menu>();
    public DbSet<MenuComposition> MenuCompositions => Set<MenuComposition>();
    public DbSet<MenuItem> MenuItems => Set<MenuItem>();

    public DbSet<Zone> Zones => Set<Zone>();

    public DbSet<Client> Clients => Set<Client>();

    public DbSet<Commande> Commandes => Set<Commande>();
    public DbSet<CommandeItem> CommandeItems => Set<CommandeItem>();
    public DbSet<Paiement> Paiements => Set<Paiement>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Burger>().Property(p => p.Prix).HasPrecision(10, 2);
        modelBuilder.Entity<Complement>().Property(p => p.Prix).HasPrecision(10, 2);
        modelBuilder.Entity<Zone>().Property(p => p.PrixLivraison).HasPrecision(10, 2);
        modelBuilder.Entity<Commande>().Property(p => p.MontantTotal).HasPrecision(10, 2);
        modelBuilder.Entity<CommandeItem>().Property(p => p.PrixUnitaire).HasPrecision(10, 2);
        modelBuilder.Entity<CommandeItem>().Property(p => p.SousTotal).HasPrecision(10, 2);
        modelBuilder.Entity<Paiement>().Property(p => p.Montant).HasPrecision(10, 2);

        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Menu)
            .WithMany(m => m.Compositions)
            .HasForeignKey(mc => mc.MenuId);

        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Burger)
            .WithMany()
            .HasForeignKey(mc => mc.BurgerId);

        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Boisson)
            .WithMany()
            .HasForeignKey(mc => mc.BoissonId);

        modelBuilder.Entity<MenuComposition>()
            .HasOne(mc => mc.Frite)
            .WithMany()
            .HasForeignKey(mc => mc.FriteId);

        modelBuilder.Entity<MenuItem>()
            .HasOne(mi => mi.Menu)
            .WithMany(m => m.Items)
            .HasForeignKey(mi => mi.MenuId);

        modelBuilder.Entity<MenuItem>()
            .HasOne(mi => mi.Complement)
            .WithMany()
            .HasForeignKey(mi => mi.ComplementId);

        modelBuilder.Entity<Commande>()
            .HasOne(c => c.Client)
            .WithMany(cl => cl.Commandes)
            .HasForeignKey(c => c.ClientId);

        modelBuilder.Entity<Commande>()
            .HasOne(c => c.Zone)
            .WithMany()
            .HasForeignKey(c => c.ZoneId);

        modelBuilder.Entity<CommandeItem>()
            .HasOne(ci => ci.Commande)
            .WithMany(c => c.Items)
            .HasForeignKey(ci => ci.CommandeId);

        modelBuilder.Entity<Paiement>()
            .HasOne(p => p.Commande)
            .WithMany(c => c.Paiements)
            .HasForeignKey(p => p.CommandeId);

        base.OnModelCreating(modelBuilder);
    }
}
