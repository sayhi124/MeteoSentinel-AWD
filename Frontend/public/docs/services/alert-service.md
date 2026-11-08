# Alert — Mohamed Hedi Bejaoui

## I1 — Responsabilité et frontière métier

Classifier les situations et gérer le cycle de vie des alertes.. Propriété exclusive : `alert_db` ; moteur **Redis**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Règles versionnées, abonnements consentis, alertes actives, clé de déduplication et état d’acquittement.

Redis : ensembles et hashes pour état actif/TTL et contrôle atomique de doublons. AOF activé, rétention explicite ; archive durable des changements par événement vers DataHub. Limite : sans archive acquittée, ne pas purger.

Modèle logique : rule:{id} HASH ; subscription:{subject}:{id} HASH ; alert:{id} HASH ; dedup:{eventId}:{ruleVersion} STRING SET NX ; user-alerts:{subject} ZSET.

## API prévue

GET /api/alerts/me → 200,401 ; POST /api/alerts/subscriptions → 201,400,401,409 ; PATCH /api/alerts/{id}/ack → 200,401,403,404.

Ces URI décrivent la cible à développer après validation. Seuls `/api/alerts/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## I2 — Appelants, appelés et échanges

Consomme WeatherUpdated / ForecastUpdated / AirQualityUpdated ; appelle Weather, Forecast, Air Quality, User via OpenFeign ; produit AlertRaised / AlertAcknowledged.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Retry borné, file de quarantaine ; aucune nouvelle classe rassurante si données manquantes ; conserver état inconnu/stale et informer le client.

## I3 — Projet et contribution

Projet Java 17/Spring Boot indépendant dans `Backend/alert-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.

Le responsable doit relire, modifier et committer personnellement sa fiche, son modèle et ses contrats sur sa propre branche. Les commits de génération ne prouvent pas sa contribution. Présenter son service soi-même est indispensable : sinon I1 à I3 valent zéro selon la grille.

## I4 — Questions à savoir expliquer

1. Quelle capacité métier possèdes-tu ? Pourquoi ces données ne sont-elles pas dans le service voisin ?
2. Pourquoi Redis ? Quelle limite de ce choix accepter et comment la traiter ?
3. Qui t’appelle, qui appelles-tu, avec quelles données et quels timeouts ?
4. Que se passe-t-il si un appel échoue ou si un événement arrive deux fois ?
5. Quelle différence entre Gateway, Eureka, Config Server et Keycloak ?

## Exposé individuel — 40 secondes

« Je suis Mohamed Hedi Bejaoui. Mon service Alert gère classifier les situations et gérer le cycle de vie des alertes. Je possède alert_db sous Redis, pour les raisons de mon modèle. Je montre le diagramme et les URI prévus, puis un appel synchrone ou un événement pertinent. En cas de panne, l’état incomplet est explicite. Le projet compile et démarre, mais le métier sera développé après validation. »
