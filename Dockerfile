# 1. Base image PHP avec extensions nécessaires
FROM php:8.3-cli

# 2. Installer dépendances système + extensions PHP
RUN apt-get update && apt-get install -y \
    git unzip libonig-dev libzip-dev \
    && docker-php-ext-install pdo pdo_mysql mbstring zip \
    && apt-get clean

# 3. Installer Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# 4. Copier le projet
WORKDIR /app
COPY . /app

# 5. Installer les dépendances PHP
RUN composer install --no-dev --optimize-autoloader

# 6. Exposer le port Render utilisera
EXPOSE 10000

# 7. Commande pour lancer Symfony
CMD ["php", "-S", "0.0.0.0:10000", "-t", "public"]
