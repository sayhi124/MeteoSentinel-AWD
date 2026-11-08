# User Service

Responsable : **Ahmed Ben Mahmoud**. Base exclusive prévue : `user_db`.

Gérer profils métier, villes favorites et consentements.

Projet Spring Boot initialisé. Ce squelette compile et démarre ; le métier renvoie volontairement 501 jusqu’à validation de conception. Modèles Java dans `src/main/java/tn/esprit/meteosentinel/model`. Contrats, base justifiée, API, interlocuteurs et pannes : [fiche individuelle](../../Documentation/Services/user-service.md).

Port local : 8215. GET `/actuator/health` et `/api/users/design`. Lancement depuis la racine avec `scripts/start.ps1`.

Base prévue : **MariaDB**, exclusive au service. Le mode local démarre sans base externe, sans persistance et sans simulation métier. Le profil `databases` charge la configuration de la base prévue. Keycloak est activé avec le profil `secure` ; le mode local est explicitement sans authentification et lié à 127.0.0.1. L'état `databaseConnected:false` indique qu'aucun adaptateur métier n'a encore été validé.
