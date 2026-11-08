# Air Quality — Kaouther Halouti

## Responsabilité et frontière métier

Fournir la pollution prévue et son indice documenté.. Propriété exclusive : `air_quality_db` ; moteur **MySQL**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Mesures modélisées AQI avec échelle documentée, PM2.5, PM10, ozone, date de validité et provenance CAMS/Open-Meteo.

MySQL : séries structurées, contraintes et index ville/validAt. AQI européen conservé comme indice fournisseur ; ne pas le présenter comme classe officielle du projet.

Modèle logique : AirQualitySnapshot(id UUID PK, cityCode, validAt, fetchedAt, source); PollutantReading(snapshotId FK, europeanAqi, pm25, pm10, ozone).

## API prévue

GET /api/air-quality/{city} → 200,400,404,503 ; GET /api/air-quality/{city}/history → 200,400.

Ces URI décrivent la cible à développer après validation. Seuls `/api/air-quality/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## Appelants, appelés et échanges

Produit AirQualityUpdated ; appelé par Activity et Alert ; DataHub consomme les événements.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : 503 si fournisseur indisponible et pas de cache frais ; indice absent distinct de zéro ; timeouts et contrôle de cohérence des unités.

## Projet et configuration

Projet Java 17/Spring Boot indépendant dans `Backend/air-quality-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.
