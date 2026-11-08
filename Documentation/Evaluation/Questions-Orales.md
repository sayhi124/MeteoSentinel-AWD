# Préparation aux questions individuelles

Chaque étudiant répond avec ses propres exemples, sans réciter uniquement le support.

## Pourquoi un contexte borné ?

Il possède une capacité métier et ses règles ; ce n’est pas un découpage par table. Weather décrit le présent, Forecast les versions futures, Activity la décision, Alert le suivi ; DataHub analyse des événements.

## Gateway ou Eureka ?

Le Gateway reçoit et route les requêtes. Eureka indique où sont les instances. Les requêtes métier ne passent pas par Eureka.

## Rôle de Config Server ?

Centraliser la configuration versionnée. Pas de mots de passe dans Git. Les secrets viennent de l’environnement. Un service peut conserver sa configuration déjà chargée si Config Server tombe.

## Pourquoi une base exclusive ?

Autonomie, migrations indépendantes, responsabilité claire. Les autres services utilisent API/événements, jamais ses tables. Sept moteurs ajoutent un coût de maintenance assumé dans cette proposition pédagogique.

## OpenFeign ou RabbitMQ ?

OpenFeign convient à une réponse immédiate pour la requête utilisateur ; RabbitMQ découple ingestion et traitements. Un événement peut être livré plusieurs fois : eventId, idempotence et reprise sont nécessaires.

## Pourquoi Qdrant ?

Recherche de similarité sémantique sur le catalogue d’activités. Le modèle, sa dimension et sa version sont figés ; les contraintes météo sont ensuite évaluées numériquement. La similarité n’est pas un score de sécurité.

## Redis suffit-il pour Alert ?

État actif, TTL et déduplication atomique sont adaptés. AOF, sauvegarde et archive d’événements sont nécessaires ; une panne ou une perte de message ne doit pas effacer silencieusement une alerte.

## Comment éviter les doublons ClickHouse ?

eventId stable, moteur ReplacingMergeTree et lecture agrégée dédupliquée. Les fusions sont éventuelles ; ne pas revendiquer une contrainte unique OLTP.

## Keycloak ou User Service ?

Keycloak gère identité et mots de passe ; User conserve profil applicatif, préférences et consentement. Le JWT est validé puis les autorisations métier sont appliquées.

## Quel est l’état réel du code ?

Squelettes compilables, services démarrables, configuration et découverte réelles. Les endpoints métier renvoient 501 ; aucun système météo ou vectoriel achevé n’est annoncé.

## Comment évaluer une prévision ?

Aligner validAt, variable, unités, horizon et version, puis comparer à une référence datée indépendante. Réanalyse et station ne sont pas équivalentes. Pas de précision inventée.

## Que faire en panne ?

Timeout borné, 503 si donnée indispensable absente ; cache daté si contrat le permet ; états unknown/stale. Jamais de météo inventée ni de désactivation de sécurité.