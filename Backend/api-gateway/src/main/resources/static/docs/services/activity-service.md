# Activity — Mohamed Aziz Sayhi

## I1 — Responsabilité et frontière métier

Classer les créneaux selon météo, air et préférences.. Propriété exclusive : `activity_db` ; moteur **Qdrant (vectoriel)**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Catalogue d’activités et embeddings sémantiques avec modèle/dimension/version ; payload explicatif ; candidats et contexte météo utilisés à la requête.

Qdrant : recherche vectorielle filtrée sur catalogue d’activités. Le vecteur compare préférences/descriptions ; les contraintes météo/AQI sont contrôlées ensuite sur les données numériques. Aucun score vectoriel présenté comme une probabilité de sécurité.

Modèle logique : Collection activity_catalog : point UUID, vector[384] normalisé avec modèle paraphrase-multilingual-MiniLM-L12-v2 version figée, payload {activityCode,title,description,embeddingVersion,minAge?,tags}. Dimension/modèle à vérifier avant ingestion, toute migration utilise une nouvelle collection. Les résultats météo ne sont pas stockés comme vérité vectorielle.

## API prévue

POST /api/activities/search → 200,400,401,503 ; POST /api/activities/recommendations → 200,400,401,422,503.

Ces URI décrivent la cible à développer après validation. Seuls `/api/activities/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## I2 — Appelants, appelés et échanges

Appelle Weather, Forecast, Air Quality et User via OpenFeign ; recherche Qdrant via client HTTP interne ; produit RecommendationComputed (identité pseudonymisée). Appelé par frontend via Gateway.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Dépendance indispensable indisponible : 503 sans classement. Qdrant indisponible : recherche catalogue indisponible, aucune similarité inventée. Une activité choisie explicitement pourra être évaluée sans recherche si validé au contrat.

## I3 — Projet et contribution

Projet Java 17/Spring Boot indépendant dans `Backend/activity-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.

Le responsable doit relire, modifier et committer personnellement sa fiche, son modèle et ses contrats sur sa propre branche. Les commits de génération ne prouvent pas sa contribution. Présenter son service soi-même est indispensable : sinon I1 à I3 valent zéro selon la grille.

## I4 — Questions à savoir expliquer

1. Quelle capacité métier possèdes-tu ? Pourquoi ces données ne sont-elles pas dans le service voisin ?
2. Pourquoi Qdrant (vectoriel) ? Quelle limite de ce choix accepter et comment la traiter ?
3. Qui t’appelle, qui appelles-tu, avec quelles données et quels timeouts ?
4. Que se passe-t-il si un appel échoue ou si un événement arrive deux fois ?
5. Quelle différence entre Gateway, Eureka, Config Server et Keycloak ?

## Exposé individuel — 40 secondes

« Je suis Mohamed Aziz Sayhi. Mon service Activity gère classer les créneaux selon météo, air et préférences. Je possède activity_db sous Qdrant (vectoriel), pour les raisons de mon modèle. Je montre le diagramme et les URI prévus, puis un appel synchrone ou un événement pertinent. En cas de panne, l’état incomplet est explicite. Le projet compile et démarre, mais le métier sera développé après validation. »
