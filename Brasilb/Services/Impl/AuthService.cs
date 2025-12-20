using Brasilb.Data;
using Brasilb.Models;
using Microsoft.EntityFrameworkCore;

namespace Brasilb.Services.Impl;

public class AuthService : IAuthService
{
    private readonly BrasilbDbContext _db;

    public AuthService(BrasilbDbContext db)
    {
        _db = db;
    }

    public Task<Client?> GetClientByIdAsync(int id)
    {
        return _db.Clients.FirstOrDefaultAsync(c => c.Id == id && !c.Archived);
    }

    public Task<Client?> GetClientByEmailAsync(string email)
    {
        return _db.Clients.FirstOrDefaultAsync(c => c.Email == email && !c.Archived);
    }

    public async Task<Client?> ValidateCredentialsAsync(string email, string password)
    {
        var client = await GetClientByEmailAsync(email);
        if (client == null)
        {
            return null;
        }

        return client.Password == password ? client : null;
    }

    public async Task<(bool Ok, string ErrorMessage, Client? Client)> RegisterClientAsync(Client client)
    {
        var existing = await GetClientByEmailAsync(client.Email);
        if (existing != null)
        {
            return (false, "Email déjà utilisé", null);
        }

        client.Archived = false;
        client.CreatedAt = DateTime.UtcNow;

        _db.Clients.Add(client);
        await _db.SaveChangesAsync();

        return (true, string.Empty, client);
    }
}
