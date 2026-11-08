# Organisation Git et suivi

Branche d’intégration `main`, branches `feature/weather`, `feature/forecast`, `feature/alert`, `feature/air-quality`, `feature/user`, `feature/activity`, `feature/datahub`. Une PR par changement, revue par un autre étudiant, Maven verify obligatoire en CI. Les commits initiaux sont une préparation assistée ; ils ne représentent pas des contributions personnelles des six membres.

Chaque responsable : cloner, créer sa branche, relire sa fiche et le modèle Java, modifier réellement un contrat ou une justification, lancer Maven, committer sous sa propre identité et ouvrir une PR. Ne pas changer l’auteur d’un commit pour simuler une contribution. Tous participent au service commun DataHub et aux revues.

Outil de gestion retenu : **GitHub Issues + jalons + labels par service**. Un ticket contient responsable attendu, critères de recette et statut via labels `todo`, `in-progress`, `review`, `done`. Les comptes GitHub des coéquipiers n’étant pas fournis, aucune invitation ni assignation de compte supposé. Un tableau Kanban local est fourni dans TASKS.md ; aucun GitHub Project inexistant n’est revendiqué.

## Planning

S4 : revue des frontières, modèles, APIs, nombre de services, bases et soutenance de conception. Après validation : S5 squelettes/bases et ingestion ; S6 OpenFeign et contrats ; S7 RabbitMQ et reprise ; S8 sécurité et consentement ; S9 Docker et recette ; S10 soutenance finale. Planning proposé, à adapter au calendrier enseignant.

## Risques

Sept moteurs augmentent l’exploitation : images et configurations figées, un profil de développement par service et intégration progressive. Modèle vectoriel non évalué : jeu de requêtes de pertinence relu par l’équipe avant l’intégration. API externe : cache daté et limites de débit. Données manquantes : réponse explicite. Authentification : JWT validé côté Gateway et service. Commits tardifs et parole déséquilibrée : chaque membre relit sa partie et répète.
