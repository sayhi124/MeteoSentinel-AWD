# Forecast — Dhia Borzli

## I1 — Responsabilité et frontière métier

Servir les prévisions horaires et préserver leurs versions.. Propriété exclusive : `forecast_db` ; moteur **MongoDB**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Runs immuables de prévision, heures imbriquées, issuedAt si disponible, fetchedAt, variable, horizon, provenance.

MongoDB : document ForecastRun avec tableau hours ; index unique ville/source/version, TTL seulement sur caches, jamais sur références évaluées.

Modèle logique : ForecastRun(_id, cityCode, issuedAt?, fetchedAt, source, hours:[{validAt,temperatureC,windKmh,rainMm,rainProbabilityPct}]).

## API prévue

GET /api/forecasts/{city} → 200,400,404,503 ; GET /api/forecasts/{city}/runs → 200,400.

Ces URI décrivent la cible à développer après validation. Seuls `/api/forecasts/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## I2 — Appelants, appelés et échanges

Produit ForecastUpdated ; appelé par Activity et Alert ; DataHub reçoit les versions.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Timeout fournisseur, cache étiqueté ; 503 si heures manquantes indispensables ; aucune interpolation silencieuse.

## I3 — Projet et contribution

Projet Java 17/Spring Boot indépendant dans `Backend/forecast-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.

Le responsable doit relire, modifier et committer personnellement sa fiche, son modèle et ses contrats sur sa propre branche. Les commits de génération ne prouvent pas sa contribution. Présenter son service soi-même est indispensable : sinon I1 à I3 valent zéro selon la grille.

## I4 — Questions à savoir expliquer

1. Quelle capacité métier possèdes-tu ? Pourquoi ces données ne sont-elles pas dans le service voisin ?
2. Pourquoi MongoDB ? Quelle limite de ce choix accepter et comment la traiter ?
3. Qui t’appelle, qui appelles-tu, avec quelles données et quels timeouts ?
4. Que se passe-t-il si un appel échoue ou si un événement arrive deux fois ?
5. Quelle différence entre Gateway, Eureka, Config Server et Keycloak ?

## Exposé individuel — 40 secondes

« Je suis Dhia Borzli. Mon service Forecast gère servir les prévisions horaires et préserver leurs versions. Je possède forecast_db sous MongoDB, pour les raisons de mon modèle. Je montre le diagramme et les URI prévus, puis un appel synchrone ou un événement pertinent. En cas de panne, l’état incomplet est explicite. Le projet compile et démarre, mais le métier sera développé après validation. »
