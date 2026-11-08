# DataHub — Toute l’équipe

## Responsabilité et frontière métier

Historiser les événements et produire des rapports de qualité et d’évaluation.. Propriété exclusive : `datahub_db` ; moteur **ClickHouse**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Projections analytiques d’événements, provenance, contrôles de qualité, versions de prévision et références temporelles. Pas de données de profil directement identifiantes.

ClickHouse : stockage en colonnes et agrégats par ville/date/horizon sur événements append-only. Déduplication logique par eventId via ReplacingMergeTree et lecture FINAL/argMax pour les rapports ; pas de garantie d’unicité OLTP.

Modèle logique : EventLog(eventId UUID, eventType, occurredAt, source, aggregateId, schemaVersion, payload, receivedAt) ENGINE ReplacingMergeTree(receivedAt) ORDER BY eventId ; ForecastEvaluation(runId, validAt, horizonHours, variable, referenceKind, absError).

## API prévue

GET /api/datahub/quality → 200,400,401 ; GET /api/datahub/forecast-errors → 200,400,401,422 ; GET /api/datahub/events → 200,400,401.

Ces URI décrivent la cible à développer après validation. Seuls `/api/datahub/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## Appelants, appelés et échanges

Commun à toute l’équipe ; consomme WeatherUpdated / ForecastUpdated / AirQualityUpdated / AlertRaised / PreferencesChanged / RecommendationComputed ; aucun accès direct aux autres bases.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Acquittement RabbitMQ après écriture réussie ; redélivrance et déduplication logique, retry borné et quarantaine ; aucun rapport « complet » si des événements manquent.

## Projet et configuration

Projet Java 17/Spring Boot indépendant dans `Backend/datahub-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.
