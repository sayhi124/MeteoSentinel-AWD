# Communications et pannes

## REST / OpenFeign

Frontend → Gateway → Activity. Activity appelle Weather, Forecast, Air Quality et User via OpenFeign et découverte Eureka. Alert consulte ces mêmes contrats au besoin. Données : cityCode, plage temporelle, subject autorisé, contraintes, valeurs météo/AQI avec unités et provenance. Timeout connexion 2s, lecture 4s, aucun retry implicite sur commande non idempotente. Budgets agrégés et parallélisation à valider au développement. JWT transmis uniquement vers les services autorisés et identité /me contrôlée ; ce relais métier est prévu, non implémenté dans les stubs.

Qdrant est consulté uniquement par Activity : embedding du texte utilisateur, filtre catalogue et recherche top-k ; les contraintes météo restent numériques. Le modèle d’embedding et sa dimension sont versionnés. DataHub n’appelle aucune base métier.

## RabbitMQ

Exchange durable topic `meteo.events`. Une file par consommateur : `alert.events`, `datahub.events`. Routage par type/version, schéma d’événement commun, publisher confirms. Outbox transactionnelle pour SQL ; mécanisme propre Mongo/Redis/Qdrant à concevoir et valider (transaction Mongo en replica set, stream Redis, tâche d’ingestion reprise). Ne pas prétendre à une transaction atomique générique entre des moteurs hétérogènes et RabbitMQ.

Message : eventId, eventType, schemaVersion=1, producer, occurredAt UTC, aggregateId, correlationId, payload ; pas de mot de passe ni données personnelles inutiles. Les consommateurs acquittent après traitement durable, retry exponentiel borné (3), puis file de quarantaine. Rejeu manuel tracé. Aucun « exactly once » annoncé : livraison au moins une fois et effets idempotents.

## Matrice

|Producteur|Appelants REST|Événements|Consommateurs|
|---|---|---|---|
|Weather|Activity, Alert|WeatherUpdated|Alert, DataHub|
|Forecast|Activity, Alert|ForecastUpdated|Alert, DataHub|
|Air Quality|Activity, Alert|AirQualityUpdated|Alert, DataHub|
|User|Activity, Alert|PreferencesChanged, ConsentWithdrawn|Alert, DataHub (payload minimisé)|
|Activity|Frontend via Gateway|RecommendationComputed|DataHub|
|Alert|Frontend via Gateway|AlertRaised, AlertAcknowledged|DataHub|
|DataHub|Administrateur via Gateway|aucun obligatoire|—|

## Pannes

503 si dépendance indispensable absente ; données périmées signalées avec date et source ; pas de score inventé. Gateway renvoie 503 s’il n’existe pas d’instance disponible. Config Server requis au démarrage ; instance déjà démarrée conserve sa configuration chargée. Eureka est un annuaire, pas le chemin des messages métier. Keycloak indisponible empêche une nouvelle connexion ; un JWT existant peut être validé localement avec des clés en cache dans sa période de validité. Pas de désactivation automatique de sécurité. Si DataHub est absent, les files conservent les événements jusqu’à la limite de rétention monitorée.
