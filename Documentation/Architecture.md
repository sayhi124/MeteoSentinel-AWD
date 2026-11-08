# Architecture proposée — Validation 1

Frontend React → Spring Cloud Gateway → sept services métier Spring Boot. Eureka est l’annuaire des instances. Config Server fournit configuration centralisée, sans secrets dans Git. Keycloak gère OIDC/PKCE et émet les JWT ; Gateway et services valident signature, émetteur, expiration, audience puis les autorisations du métier. Keycloak ne fait pas transiter chaque requête métier. Les bases sont exclusives, une par service, avec credentials et réseaux isolés.

Spring Boot 3.5.16 / Java 17 / Spring Cloud 2025.0.1. Ces versions Boot/Cloud appartiennent à la même ligne compatible : https://spring.io/projects/spring-cloud/ . OpenFeign pour les contrats REST ; RabbitMQ pour les événements ; Docker Compose pour l’environnement cible.

## Services, bases et responsables

- Weather : Nour Maroufi — **PostgreSQL**, `weather_db`.
- Forecast : Dhia Borzli — **MongoDB**, `forecast_db`.
- Alert : Mohamed Hedi Bejaoui — **Redis**, `alert_db`.
- Air Quality : Kaouther Halouti — **MySQL**, `air_quality_db`.
- User : Ahmed Ben Mahmoud — **MariaDB**, `user_db`.
- Activity : Mohamed Aziz Sayhi — **Qdrant (vectoriel)**, `activity_db`.
- DataHub : Toute l’équipe — **ClickHouse**, `datahub_db`.

## Technologie avancée

Activity utilise la recherche vectorielle Qdrant ; DataHub commun utilise ClickHouse pour la traçabilité et l’évaluation des données. Le document nomme explicitement le rôle avancé du service commun. Ces technologies apportent une recherche sémantique et de l’analytique, sans présenter un modèle ML entraîné comme réalisé.

## Dimensionnement
+
+Le projet comporte six capacités métier individuelles et un service commun DataHub. Les services sont intégrés progressivement : contrats et données, appels synchrones, événements, sécurité puis déploiement. Les choix de moteurs distincts impliquent des configurations et des stratégies de reprise propres à chaque service.
+
+## Ce qui fonctionne au stade S4

Les dix processus Spring Boot démarrent ; Config Server, Eureka et Gateway sont réels ; les routes /design exposent l’état de conception. Chaque service possède ses propres modèles et configuration de base prévue. Le portail React affiche les statuts réels des /design. Aucun catalogue vectoriel, métier météo, notification, broker ni base hétérogène n’est prétendu opérationnel en mode local. Le profil secure est codé ; la stack Docker nécessite une vérification ultérieure car Docker n’est pas installé sur la machine de préparation.
