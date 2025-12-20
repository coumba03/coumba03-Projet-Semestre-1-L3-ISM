using Brasilb.Models;
using Brasilb.ViewModels;

namespace Brasilb.Services;

public interface IOrderService
{
    Task<(bool Ok, string ErrorMessage, int? CommandeId)> CreateOrderAndPayAsync(int clientId, CheckoutViewModel checkout, CartViewModel cart);
    Task<List<Commande>> GetOrdersForClientAsync(int clientId);
}
