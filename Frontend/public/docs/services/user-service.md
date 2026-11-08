# User — Ahmed Ben Mahmoud

## I1 — Responsabilité et frontière métier

Gérer profils métier, villes favorites et consentements.. Propriété exclusive : `user_db` ; moteur **MariaDB**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Profil applicatif indexé par subject Keycloak, préférences, favoris, consentement versionné et date de retrait. Aucun mot de passe.

MariaDB : relations profil/préférences/favoris, transactions et contraintes d’unicité. Même famille SQL que MySQL mais instance et credentials distincts.

Modèle logique : UserProfile(subject VARCHAR PK, displayName, consentVersion, consentAt); Preference(subject FK, activityType, maxWind, maxAqi); FavoriteCity(subject FK, cityCode).

## API prévue

GET /api/users/me → 200,401,404 ; PUT /api/users/me/preferences → 200,400,401 ; DELETE /api/users/me/consent → 204,401.

Ces URI décrivent la cible à développer après validation. Seuls `/api/users/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## I2 — Appelants, appelés et échanges

Appelé par Activity et Alert avec identité ou rôle de service contrôlé ; produit PreferencesChanged / ConsentWithdrawn.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Profil indisponible : pas de personnalisation silencieuse. 503 ; accès /me lié au sub du JWT. Retrait de consentement prioritaire et propagé.

## I3 — Projet et contribution

Projet Java 17/Spring Boot indépendant dans `Backend/user-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.

Le responsable doit relire, modifier et committer personnellement sa fiche, son modèle et ses contrats sur sa propre branche. Les commits de génération ne prouvent pas sa contribution. Présenter son service soi-même est indispensable : sinon I1 à I3 valent zéro selon la grille.

## I4 — Questions à savoir expliquer

1. Quelle capacité métier possèdes-tu ? Pourquoi ces données ne sont-elles pas dans le service voisin ?
2. Pourquoi MariaDB ? Quelle limite de ce choix accepter et comment la traiter ?
3. Qui t’appelle, qui appelles-tu, avec quelles données et quels timeouts ?
4. Que se passe-t-il si un appel échoue ou si un événement arrive deux fois ?
5. Quelle différence entre Gateway, Eureka, Config Server et Keycloak ?

## Exposé individuel — 40 secondes

« Je suis Ahmed Ben Mahmoud. Mon service User gère gérer profils métier, villes favorites et consentements. Je possède user_db sous MariaDB, pour les raisons de mon modèle. Je montre le diagramme et les URI prévus, puis un appel synchrone ou un événement pertinent. En cas de panne, l’état incomplet est explicite. Le projet compile et démarre, mais le métier sera développé après validation. »
