# Weather — Nour Maroufi

## Responsabilité et frontière métier

Acquérir et historiser les conditions météo courantes.. Propriété exclusive : `weather_db` ; moteur **PostgreSQL**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Snapshots horodatés, ville, coordonnées, température, vent, humidité, fournisseur, fetchedAt, qualityFlags.

PostgreSQL : contraintes, index ville/date et transactions pour snapshots et outbox. Aucune lecture interbase.

Modèle logique : WeatherSnapshot(id UUID PK, cityCode, fetchedAt, source); WeatherReading(snapshotId FK, temperatureC, windKmh, humidityPct).

## API prévue

GET /api/weather/{city} → 200,400,404,503 ; GET /api/weather/{city}/history → 200,400.

Ces URI décrivent la cible à développer après validation. Seuls `/api/weather/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## Appelants, appelés et échanges

Produit WeatherUpdated ; appelé par Activity et Alert ; aucune dépendance métier synchrone sortante.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : API fournisseur : timeout 2s/4s ; cache au plus 30 minutes signalé avec sa date ; 503 si aucune donnée exploitable.

## Projet et configuration

Projet Java 17/Spring Boot indépendant dans `Backend/weather-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.
