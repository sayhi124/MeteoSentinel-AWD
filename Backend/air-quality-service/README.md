# Air Quality Service

Responsable : **Kaouther Nalouti**. Base exclusive prévue : `air_quality_db`.

Fournir la pollution prévue et son indice documenté.

Projet Spring Boot initialisé. Ce squelette compile et démarre ; le métier renvoie volontairement 501 jusqu’à validation de conception. Modèles Java dans `src/main/java/tn/esprit/meteosentinel/model`. Contrats, base justifiée, API, interlocuteurs et pannes : [fiche individuelle](../../Documentation/Services/air-quality-service.md).

Port local : 8214. GET `/actuator/health` et `/api/air-quality/design`. Lancement depuis la racine avec `scripts/start.ps1`.

Base prévue : **MySQL**, exclusive au service. Le mode local démarre sans base externe, sans persistance et sans simulation métier. Le profil `databases` charge la configuration de la base prévue. Keycloak est activé avec le profil `secure` ; le mode local est explicitement sans authentification et lié à 127.0.0.1. L'état `databaseConnected:false` indique qu'aucun adaptateur métier n'a encore été validé.
