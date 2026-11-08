# User — Ahmed Ben Mahmoud

## Responsabilité et frontière métier

Gérer profils métier, villes favorites et consentements.. Propriété exclusive : `user_db` ; moteur **MariaDB**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Profil applicatif indexé par subject Keycloak, préférences, favoris, consentement versionné et date de retrait. Aucun mot de passe.

MariaDB : relations profil/préférences/favoris, transactions et contraintes d’unicité. Même famille SQL que MySQL mais instance et credentials distincts.

Modèle logique : UserProfile(subject VARCHAR PK, displayName, consentVersion, consentAt); Preference(subject FK, activityType, maxWind, maxAqi); FavoriteCity(subject FK, cityCode).

## API prévue

GET /api/users/me → 200,401,404 ; PUT /api/users/me/preferences → 200,400,401 ; DELETE /api/users/me/consent → 204,401.

Ces URI décrivent la cible à développer après validation. Seuls `/api/users/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## Appelants, appelés et échanges

Appelé par Activity et Alert avec identité ou rôle de service contrôlé ; produit PreferencesChanged / ConsentWithdrawn.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Profil indisponible : pas de personnalisation silencieuse. 503 ; accès /me lié au sub du JWT. Retrait de consentement prioritaire et propagé.

## Projet et configuration

Projet Java 17/Spring Boot indépendant dans `Backend/user-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.
