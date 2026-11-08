# Air Quality — Kaouther Halouti

## I1 — Responsabilité et frontière métier

Fournir la pollution prévue et son indice documenté.. Propriété exclusive : `air_quality_db` ; moteur **MySQL**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Mesures modélisées AQI avec échelle documentée, PM2.5, PM10, ozone, date de validité et provenance CAMS/Open-Meteo.

MySQL : séries structurées, contraintes et index ville/validAt. AQI européen conservé comme indice fournisseur ; ne pas le présenter comme classe officielle du projet.

Modèle logique : AirQualitySnapshot(id UUID PK, cityCode, validAt, fetchedAt, source); PollutantReading(snapshotId FK, europeanAqi, pm25, pm10, ozone).

## API prévue

GET /api/air-quality/{city} → 200,400,404,503 ; GET /api/air-quality/{city}/history → 200,400.

Ces URI décrivent la cible à développer après validation. Seuls `/api/air-quality/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## I2 — Appelants, appelés et échanges

Produit AirQualityUpdated ; appelé par Activity et Alert ; DataHub consomme les événements.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : 503 si fournisseur indisponible et pas de cache frais ; indice absent distinct de zéro ; timeouts et contrôle de cohérence des unités.

## I3 — Projet et contribution

Projet Java 17/Spring Boot indépendant dans `Backend/air-quality-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.

Le responsable doit relire, modifier et committer personnellement sa fiche, son modèle et ses contrats sur sa propre branche. Les commits de génération ne prouvent pas sa contribution. Présenter son service soi-même est indispensable : sinon I1 à I3 valent zéro selon la grille.

## I4 — Questions à savoir expliquer

1. Quelle capacité métier possèdes-tu ? Pourquoi ces données ne sont-elles pas dans le service voisin ?
2. Pourquoi MySQL ? Quelle limite de ce choix accepter et comment la traiter ?
3. Qui t’appelle, qui appelles-tu, avec quelles données et quels timeouts ?
4. Que se passe-t-il si un appel échoue ou si un événement arrive deux fois ?
5. Quelle différence entre Gateway, Eureka, Config Server et Keycloak ?

## Exposé individuel — 40 secondes

« Je suis Kaouther Halouti. Mon service Air Quality gère fournir la pollution prévue et son indice documenté. Je possède air_quality_db sous MySQL, pour les raisons de mon modèle. Je montre le diagramme et les URI prévus, puis un appel synchrone ou un événement pertinent. En cas de panne, l’état incomplet est explicite. Le projet compile et démarre, mais le métier sera développé après validation. »
