# Weather Service

Responsable : **Nour Maroufi**. Base exclusive prévue : `weather_db`.

Acquérir et historiser les conditions météo courantes.

Projet Spring Boot initialisé. Ce squelette compile et démarre ; le métier renvoie volontairement 501 jusqu’à validation de conception. Modèles Java dans `src/main/java/tn/esprit/meteosentinel/model`. Contrats, base justifiée, API, interlocuteurs et pannes : [fiche individuelle](../../Documentation/Services/weather-service.md).

Port local : 8211. GET `/actuator/health` et `/api/weather/design`. Lancement depuis la racine avec `scripts/start.ps1`.

Base prévue : **PostgreSQL**, exclusive au service. Le mode local démarre sans base externe, sans persistance et sans simulation métier. Le profil `databases` charge la configuration de la base prévue. Keycloak est activé avec le profil `secure` ; le mode local est explicitement sans authentification et lié à 127.0.0.1. L'état `databaseConnected:false` indique qu'aucun adaptateur métier n'a encore été validé.
