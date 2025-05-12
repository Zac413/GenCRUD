.PHONY: reset-migrations create-db migrate

# Supprime les anciennes migrations et crée une nouvelle base de données
reset-migrations:
	rm -rf migrations/*
	touch migrations/.gitkeep

create-db:
	php bin/console doctrine:database:create --if-not-exists || true

generate-migrations:
	php bin/console make:migration

migrate:
	php bin/console doctrine:migrations:migrate --no-interaction

schema-update:
	php bin/console doctrine:schema:update --force

init-db: create-db reset-migrations generate-migrations migrate
