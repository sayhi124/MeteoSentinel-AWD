# DataHub — Toute l’équipe

## I1 — Responsabilité et frontière métier

Historiser les événements et produire des rapports de qualité et d’évaluation.. Propriété exclusive : `datahub_db` ; moteur **ClickHouse**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Projections analytiques d’événements, provenance, contrôles de qualité, versions de prévision et références temporelles. Pas de données de profil directement identifiantes.

ClickHouse : stockage en colonnes et agrégats par ville/date/horizon sur événements append-only. Déduplication logique par eventId via ReplacingMergeTree et lecture FINAL/argMax pour les rapports ; pas de garantie d’unicité OLTP.

Modèle logique : EventLog(eventId UUID, eventType, occurredAt, source, aggregateId, schemaVersion, payload, receivedAt) ENGINE ReplacingMergeTree(receivedAt) ORDER BY eventId ; ForecastEvaluation(runId, validAt, horizonHours, variable, referenceKind, absError).

## API prévue

GET /api/datahub/quality → 200,400,401 ; GET /api/datahub/forecast-errors → 200,400,401,422 ; GET /api/datahub/events → 200,400,401.

Ces URI décrivent la cible à développer après validation. Seuls `/api/datahub/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## I2 — Appelants, appelés et échanges

Commun à toute l’équipe ; consomme WeatherUpdated / ForecastUpdated / AirQualityUpdated / AlertRaised / PreferencesChanged / RecommendationComputed ; aucun accès direct aux autres bases.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Acquittement RabbitMQ après écriture réussie ; redélivrance et déduplication logique, retry borné et quarantaine ; aucun rapport « complet » si des événements manquent.

## I3 — Projet et contribution

Projet Java 17/Spring Boot indépendant dans `Backend/datahub-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.

Le responsable doit relire, modifier et committer personnellement sa fiche, son modèle et ses contrats sur sa propre branche. Les commits de génération ne prouvent pas sa contribution. Présenter son service soi-même est indispensable : sinon I1 à I3 valent zéro selon la grille.

## I4 — Questions à savoir expliquer

1. Quelle capacité métier possèdes-tu ? Pourquoi ces données ne sont-elles pas dans le service voisin ?
2. Pourquoi ClickHouse ? Quelle limite de ce choix accepter et comment la traiter ?
3. Qui t’appelle, qui appelles-tu, avec quelles données et quels timeouts ?
4. Que se passe-t-il si un appel échoue ou si un événement arrive deux fois ?
5. Quelle différence entre Gateway, Eureka, Config Server et Keycloak ?

## Exposé individuel — 40 secondes

« Je suis Toute l’équipe. Mon service DataHub gère historiser les événements et produire des rapports de qualité et d’évaluation. Je possède datahub_db sous ClickHouse, pour les raisons de mon modèle. Je montre le diagramme et les URI prévus, puis un appel synchrone ou un événement pertinent. En cas de panne, l’état incomplet est explicite. Le projet compile et démarre, mais le métier sera développé après validation. »
