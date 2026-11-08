# Organisation Git et suivi

Branche d’intégration `main`, branches `feature/weather`, `feature/forecast`, `feature/alert`, `feature/air-quality`, `feature/user`, `feature/activity`, `feature/datahub`. Une PR par changement, revue par un autre étudiant, Maven verify obligatoire en CI.

Chaque microservice dispose d’une branche de développement et de tickets dédiés. Les changements sont intégrés par pull request, avec revue et vérification des contrats.

Outil de gestion retenu : **GitHub Issues + jalons + labels par service**. Un ticket contient responsable attendu, critères de recette et statut via labels `todo`, `in-progress`, `review`, `done`. Un tableau Kanban local est fourni dans TASKS.md.

## Planning

S4 : revue des frontières, modèles, APIs, nombre de services, bases et soutenance de conception. Après validation : S5 squelettes/bases et ingestion ; S6 OpenFeign et contrats ; S7 RabbitMQ et reprise ; S8 sécurité et consentement ; S9 Docker et recette ; S10 soutenance finale. Planning proposé, à adapter au calendrier enseignant.

## Risques

Sept moteurs augmentent l’exploitation : images et configurations figées, un profil de développement par service et intégration progressive. Modèle vectoriel non évalué : jeu de requêtes de pertinence relu par l’équipe avant l’intégration. API externe : cache daté et limites de débit. Données manquantes : réponse explicite. Authentification : JWT validé côté Gateway et service. Intégration tardive : stabiliser les contrats et effectuer des revues régulières.

## État vérifié du dépôt

Dépôt public : https://github.com/sayhi124/MeteoSentinel-AWD . Sept tickets sont ouverts, un par service, avec labels et jalon Validation 1. La CI Maven et React a réussi sur GitHub.
