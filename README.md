# GenCRUD

🧩 Générateur Symfony – Projet de fin d’études

Ce projet Symfony permet de générer automatiquement des entités, contrôleurs, formulaires et vues Twig à partir d’un fichier XML décrivant la structure des données et leurs relations.

⚙️ Prérequis

    PHP 8.0 ou supérieur

    Composer

    PostgreSQL installé localement

    Symfony CLI (optionnel mais recommandé)

    Java (pour le générateur de code)

🚀 Lancement du projet
1. Cloner le dépôt

git clone <url-du-repo>
cd projet/symfony

2. Configurer la base de données

Dans le répertoire projet/symfony, créez un fichier .env.local :

DB_HOST=localhost
DB_PORT=5432
DB_NAME=<nom_bdd>
DB_USER=<nom_utilisateur>
DB_PASSWORD=<mot_de_passe>

    Remplacez <nom_bdd>, <nom_utilisateur> et <mot_de_passe> par vos propres informations de connexion à PostgreSQL.
    N'oubliez pas de commenter la ligne : DATABASE_URL (nous avons modifié le doctrine.yaml pour faciliter la configuration de la BDD).

    💡 Cette configuration permet de garder les identifiants de connexion à la base de données hors du fichier .env principal.

3. Initialiser la base de données

Assurez-vous d’être dans le dossier projet/symfony, puis lancez :

php bin/console doctrine:database:create

make init-db

Ce raccourci Makefile exécute les actions suivantes :

    Création de la base de données (si elle n’existe pas)

    Suppression des anciennes migrations

    Génération des nouvelles migrations

    Application des migrations

🧼 En cas de conflit

Vous pouvez supprimer complètement la base de données avec la commande :

php bin/console doctrine:database:drop --force

▶️ Lancer le serveur Symfony

symfony server:start

ou, sans Symfony CLI :

php -S localhost:8000 -t public

🔧 Génération automatique du code

Avant d’exécuter Symfony, exécutez le générateur Java pour produire le code PHP à partir du fichier XML :

cd src/main/java
java Main (ou le lancer à partir de l'IDE)

Ce générateur lit un fichier schema.xml et produit automatiquement :

    Les entités PHP

    Les contrôleurs

    Les formulaires

    Les repositories

    Les vues Twig (index, edit, create, indexGlobal)

    La structure de style de base (base.html.twig, style.css)

Les fichiers sont générés à l'aide de templates .tpl : un format utilisé pour séparer la logique Java de la structure des fichiers générés (comme du PHP ou du HTML).
📁 Arborescence

```
projet/
├── symfony/           # Projet Symfony final
│   ├── .env.local     # Config de la BDD PostgreSQL
│   ├── Makefile       # Outils de gestion DB
│   └── ...
└── src/main/    # Générateur Java
    ├── resources/xml/schema.xml     # Fichier XML source
    └── ...

🧠 À propos

Ce projet s'inscrit dans une démarche de Rapid Application Development (RAD) visant à automatiser les tâches répétitives du développement web, notamment la création des opérations CRUD.
