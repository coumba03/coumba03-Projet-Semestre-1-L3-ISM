using Brasilb.Models;

namespace Brasilb.Services;

public interface IAuthService
{
    Task<Client?> ValidateCredentialsAsync(string email, string password);
    Task<Client?> GetClientByIdAsync(int id);
    Task<Client?> GetClientByEmailAsync(string email);
    Task<(bool Ok, string ErrorMessage, Client? Client)> RegisterClientAsync(Client client);
}
