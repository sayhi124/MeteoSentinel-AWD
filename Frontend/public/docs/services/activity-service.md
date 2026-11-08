# Activity — Mohamed Aziz Sayhi

## Responsabilité et frontière métier

Classer les créneaux selon météo, air et préférences.. Propriété exclusive : `activity_db` ; moteur **Qdrant (vectoriel)**. Aucun accès direct d’un autre service à cette base.

## Données et justification

Catalogue d’activités et embeddings sémantiques avec modèle/dimension/version ; payload explicatif ; candidats et contexte météo utilisés à la requête.

Qdrant : recherche vectorielle filtrée sur catalogue d’activités. Le vecteur compare préférences/descriptions ; les contraintes météo/AQI sont contrôlées ensuite sur les données numériques. Aucun score vectoriel présenté comme une probabilité de sécurité.

Modèle logique : Collection activity_catalog : point UUID, vector[384] normalisé avec modèle paraphrase-multilingual-MiniLM-L12-v2 version figée, payload {activityCode,title,description,embeddingVersion,minAge?,tags}. Dimension/modèle à vérifier avant ingestion, toute migration utilise une nouvelle collection. Les résultats météo ne sont pas stockés comme vérité vectorielle.

## API prévue

POST /api/activities/search → 200,400,401,503 ; POST /api/activities/recommendations → 200,400,401,422,503.

Ces URI décrivent la cible à développer après validation. Seuls `/api/activities/design` et Actuator sont réellement servis en S4. Les autres chemins du service renvoient 501. Réponses d’erreur structurées, validation de l’identité et des entrées à implémenter avec le métier.

## Appelants, appelés et échanges

Appelle Weather, Forecast, Air Quality et User via OpenFeign ; recherche Qdrant via client HTTP interne ; produit RecommendationComputed (identité pseudonymisée). Appelé par frontend via Gateway.

Synchrone OpenFeign : réponse immédiate nécessaire à la recommandation ou à la personnalisation. Charge utile : ville, dates, unités, provenance et horodatage ; préférences minimales. Asynchrone RabbitMQ : découpler ingestion, classification et analyse, sans bloquer une lecture utilisateur. Chaque événement suit `event-envelope-v1.schema.json`.

Panne : Dépendance indispensable indisponible : 503 sans classement. Qdrant indisponible : recherche catalogue indisponible, aucune similarité inventée. Une activité choisie explicitement pourra être évaluée sans recherche si validé au contrat.

## Projet et configuration

Projet Java 17/Spring Boot indépendant dans `Backend/activity-service`. Modèles de conception Java, interfaces Feign le cas échéant, configuration centralisée et tests du contrat de squelette. Le profil local démarre sans bases externes ; aucune connexion métier n’est revendiquée. Le profil `databases` prépare le moteur prévu ; Docker reste à vérifier sur une machine équipée.
