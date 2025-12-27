FROM php:8.4-cli

RUN apt-get update && apt-get install -y \
    git unzip libonig-dev libzip-dev \
    && docker-php-ext-install pdo pdo_pgsql mbstring zip \
    && apt-get clean

COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

WORKDIR /app
COPY . /app

RUN composer install --no-dev --optimize-autoloader

EXPOSE 10000

CMD ["php", "-S", "0.0.0.0:10000", "-t", "public"]
