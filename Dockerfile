FROM php:8.4-cli


ENV APP_ENV=prod
ENV APP_DEBUG=0

RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libonig-dev \
    libzip-dev \
    libpq-dev \
    && docker-php-ext-install \
    pdo \
    pdo_pgsql \
    mbstring \
    zip \
    && apt-get clean


COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /app

COPY . .


RUN composer install --no-dev --optimize-autoloader --no-interaction

EXPOSE 8000

CMD ["php", "-S", "0.0.0.0:8000", "-t", "public"]
