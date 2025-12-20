using Brasilb.Data;
using Brasilb.Models;
using Brasilb.ViewModels;
using Microsoft.EntityFrameworkCore;

namespace Brasilb.Services.Impl;

public class OrderService : IOrderService
{
    private readonly BrasilbDbContext _db;

    public OrderService(BrasilbDbContext db)
    {
        _db = db;
    }

    public async Task<(bool Ok, string ErrorMessage, int? CommandeId)> CreateOrderAndPayAsync(int clientId, CheckoutViewModel checkout, CartViewModel cart)
    {
        if (cart.Items.Count == 0)
        {
            return (false, "Panier vide", null);
        }

        Zone? zone = null;
        var deliveryFee = 0m;

        if (checkout.TypeCommande == "DELIVERY")
        {
            if (checkout.ZoneId == null)
            {
                return (false, "Veuillez choisir la zone de livraison", null);
            }

            zone = await _db.Zones.FirstOrDefaultAsync(z => z.Id == checkout.ZoneId && !z.Archived);
            if (zone == null)
            {
                return (false, "Zone de livraison introuvable", null);
            }

            deliveryFee = zone.PrixLivraison;
        }

        var client = await _db.Clients.FirstOrDefaultAsync(c => c.Id == clientId && !c.Archived);
        if (client == null)
        {
            return (false, "Client introuvable", null);
        }

        var total = cart.Total + deliveryFee;

        var commande = new Commande
        {
            ClientId = clientId,
            TypeCommande = checkout.TypeCommande,
            ZoneId = checkout.TypeCommande == "DELIVERY" ? checkout.ZoneId : null,
            Etat = "VALIDEE",
            DateCommande = DateTime.UtcNow,
            MontantTotal = total,
            Archived = false
        };

        _db.Commandes.Add(commande);
        await _db.SaveChangesAsync();

        foreach (var item in cart.Items)
        {
            var ci = new CommandeItem
            {
                CommandeId = commande.Id,
                TypeItem = item.TypeItem,
                ItemId = item.ItemId,
                Quantite = item.Quantite,
                PrixUnitaire = item.PrixUnitaire,
                SousTotal = item.SousTotal
            };

            _db.CommandeItems.Add(ci);
        }

        var paiement = new Paiement
        {
            CommandeId = commande.Id,
            Montant = total,
            ModePaiement = checkout.ModePaiement,
            DatePaiement = DateTime.UtcNow,
            Valide = true,
            ReferenceTransaction = checkout.ReferenceTransaction
        };

        _db.Paiements.Add(paiement);
        await _db.SaveChangesAsync();

        return (true, string.Empty, commande.Id);
    }

    public Task<List<Commande>> GetOrdersForClientAsync(int clientId)
    {
        return _db.Commandes
            .Include(c => c.Items)
            .Include(c => c.Paiements)
            .Include(c => c.Zone)
            .Where(c => c.ClientId == clientId && !c.Archived)
            .OrderByDescending(c => c.DateCommande)
            .ToListAsync();
    }
}
