# MétéoSentinel — Validation 1 / AWD

Une plateforme de choix d’activités et de créneaux expliqués à partir de météo, qualité de l’air et préférences. **S4 : conception et projets initialisés, pas un produit terminé.** Le métier commence après validation de l’enseignant. Les routes métier retournent volontairement **501 Not Implemented** ; le portail montre la conception et l’état réel des services.

## Équipe et données exclusives

|Membre|Microservice|Moteur prévu|
|---|---|---|
|Nour Maroufi|Weather|PostgreSQL|
|Dhia Borzli|Forecast|MongoDB|
|Mohamed Hedi Bejaoui|Alert|Redis|
|Kaouther Halouti|Air Quality|MySQL|
|Ahmed Ben Mahmoud|User|MariaDB|
|Mohamed Aziz Sayhi|Activity|Qdrant (vectoriel)|
|Toute l’équipe|DataHub|ClickHouse|

Nom du projet : MétéoSentinel. Classe et numéro de groupe : à confirmer. Six services individuels plus DataHub commun proposés ; le libellé E2 « quatre services et microservice 5 » est à confirmer avec l’enseignant pour le groupe de six. [Audit intégral de la grille](Documentation/Evaluation/Grille-Conformite.md).

## Structure

```
Backend/                  7 projets métier + Gateway + Eureka + Config Server
Frontend/                 portail React de conception
Documentation/
  Presentation/           support PPTX/PDF et plan oral de 10 minutes
  Diagrams/               architecture globale, UML et séquences
  Services/               modèles, API, échanges et pannes de chaque service
  Evaluation/             conformité et préparation aux questions
README.md
```

## Architecture

React → Spring Cloud Gateway → services Spring Boot. Eureka : découverte ; Config Server : configuration ; Keycloak : identité/JWT ; OpenFeign : REST ; RabbitMQ : événements. Chaque service possède son propre moteur et ses credentials. [Architecture complète](Documentation/Architecture.md) · [Diagramme global](Documentation/Diagrams/architecture-globale.png) · [Source modifiable](Documentation/Diagrams/architecture-globale.mmd) · [Interactions et pannes](Documentation/Interactions.md).

Activity utilise Qdrant pour la similarité sémantique du catalogue ; la pertinence vectorielle est distincte des contraintes météo numériques. DataHub commun utilise ClickHouse pour la traçabilité et les agrégats. Ces fonctions sont conçues, pas développées au stade S4.

## Lancement local — Windows / PowerShell

Prérequis : JDK 17, Maven 3.6.3+, Node 22 pour reconstruire le portail, accès initial à Maven Central et npm.

```powershell
cd Frontend
npm ci
npm run build
cd ..
mvn -B -ntp verify
powershell -ExecutionPolicy Bypass -File scripts/start.ps1 -SkipBuild
```

Ouvrir **http://127.0.0.1:8180**. Config Server : 8889 ; Eureka : 8762 ; services : 8211–8217. Les processus sont liés à 127.0.0.1. Le profil local est sans authentification et sans bases externes : il prouve uniquement démarrage, configuration et routage. Il ne prétend pas simuler le métier.

```powershell
powershell -ExecutionPolicy Bypass -File scripts/verify.ps1
powershell -ExecutionPolicy Bypass -File scripts/stop.ps1
```

Laisser 30–90 secondes au démarrage et à l’enregistrement Eureka. Les journaux sont dans `.run/` et exclus de Git. Le script stop vérifie le chemin exact du JAR avant d’arrêter un processus.

## Environnement Docker prévu

`compose.yaml` prépare sept bases dédiées, RabbitMQ et Keycloak. Copier `.env.example` vers `.env`, remplir tous les secrets localement, puis `docker compose config` et `docker compose up --build`. Les profils `secure,databases` activent JWT et configuration des bases prévues ; les adaptateurs métier restent à développer. Le profil local ne prouve pas les connexions de ces moteurs. **Docker n’était pas installé sur la machine de préparation : le démarrage Compose n’est pas revendiqué comme vérifié.**

## Soutenance et suivi

[Présentation](Documentation/Presentation/MeteoSentinel-Validation1.pptx) · [PDF](Documentation/Presentation/MeteoSentinel-Validation1.pdf) · [Dossier technique](Documentation/Dossier-Technique.pdf) · [Plan oral](Documentation/Presentation/Plan-Oral-10min.md) · [Questions individuelles](Documentation/Evaluation/Questions-Orales.md) · [Tâches](TASKS.md) · [Organisation Git](Documentation/Organisation-Git.md).

Tous les membres doivent modifier et committer personnellement après relecture et présenter leur service. Les commits initiaux assistés ne valent pas contributions de six étudiants. Les validations humaines, le nombre de services, le passage oral et le dépôt Blackboard restent à effectuer. Aucun envoi à un enseignant n’a été réalisé.

## Sources et transparence

Consignes Blackboard transmises par l’utilisateur et deux PDFs dans Documentation/Sources. Conversation partagée utilisée comme contexte secondaire, sans remplacer la grille. Préparation assistée par Codex ; illustration conceptuelle de couverture créée avec ImageGen, rendue en PNG, sans modèle 3D manipulable. L’équipe relit, corrige et déclare cette assistance selon les règles du module. Aucun résultat de précision ML ni garantie sanitaire annoncé.
