# Étape 1 : Build
FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
WORKDIR /app

# Copier le projet et restaurer les dépendances
COPY Brasilb.csproj ./
RUN dotnet restore

# Copier tout le code et publier
COPY . ./
RUN dotnet publish -c Release -o out

# Étape 2 : Runtime
FROM mcr.microsoft.com/dotnet/aspnet:9.0
WORKDIR /app
COPY --from=build /app/out ./

# Port dynamique Render
ENV ASPNETCORE_URLS=http://*:${PORT}
EXPOSE ${PORT}

# Lancer l'application
ENTRYPOINT ["dotnet", "Brasilb.dll"]
