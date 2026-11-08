# Diagrammes du projet

[Architecture globale en image](architecture-globale.png) · [Cas d’utilisation](cas-utilisation.svg)

Les schémas ci-dessous sont une conception proposée ; ils ne prétendent pas représenter un métier déjà réalisé. Config Server, Eureka et Gateway sont vérifiés localement.

## Architecture globale

```mermaid
flowchart TB
 U[Utilisateur] --> F[Frontend React]
 F -->|OIDC code + PKCE| K[Keycloak]
 F -->|HTTP + JWT| G[Spring Cloud Gateway]
 G -. découverte .-> E[Eureka]
 C[Config Server] -. configuration .-> G
 C -. configuration .-> M
 K -. JWKS / validation JWT .-> G
 subgraph M[Services Spring Boot — bases exclusives]
 W[Weather / Nour] --> WP[(PostgreSQL)]
 FC[Forecast / Dhia] --> FM[(MongoDB)]
 AL[Alert / Mohamed Hedi] --> AR[(Redis AOF)]
 AQ[Air Quality / Kaouther] --> AM[(MySQL)]
 US[User / Ahmed] --> UM[(MariaDB)]
 AC[Activity / Mohamed Aziz] --> AV[(Qdrant vectoriel)]
 DH[DataHub commun] --> DC[(ClickHouse)]
 end
 G --> W & FC & AL & AQ & US & AC & DH
 M -. enregistrement / heartbeat .-> E
 K -. JWT validé aussi par services .-> M
 AC -->|OpenFeign| W & FC & AQ & US
 W & FC & AQ & US & AC -->|événements| R[RabbitMQ]
 R --> AL & DH
 AL -->|AlertRaised| R
 subgraph D[Docker Compose — cible après validation]
 K
 G
 E
 C
 M
 R
 end
```

## Activity service classes

```mermaid
classDiagram
class ActivityEmbedding {
  +UUID id
  +String modelVersion
  +List~Float~ vector
}
class Recommendation {
  +Instant startsAt
  +Double exposureScore
  +String explanation
}
ActivityEmbedding --> Recommendation : calcul explique
```

## Air quality service classes

```mermaid
classDiagram
class AirQualitySample {
  +UUID id
  +String cityCode
  +Instant validAt
}
class AirQualityIndex {
  +Double pm25
  +Double pm10
  +String indexType
}
AirQualitySample "1" --> "1" AirQualityIndex : contient
```

## Alert service classes

```mermaid
classDiagram
class AlertRule {
  +UUID id
  +String ruleVersion
  +Double threshold
}
class WeatherAlert {
  +UUID id
  +AlertLevel level
  +AlertStatus status
}
AlertRule "1" --> "*" WeatherAlert : contient
```

## Datahub service classes

```mermaid
classDiagram
class EventRecord {
  +UUID eventId
  +String eventType
  +Instant validAt
}
class QualityReport {
  +UUID recordId
  +String checkCode
  +CheckResult result
}
EventRecord --> QualityReport : associe
```

## Forecast service classes

```mermaid
classDiagram
class ForecastRun {
  +UUID id
  +String cityCode
  +Instant  issuedAt
}
class ForecastHour {
  +Instant validAt
  +Double rainMm
  +Double apparentC
}
ForecastRun "1" --> "*" ForecastHour : contient
```

## User service classes

```mermaid
classDiagram
class UserProfile {
  +String subjectId
  +String locale
  +String units
}
class CityPreference {
  +String cityCode
  +String activity
  +Boolean notifyEnabled
}
UserProfile "1" --> "*" CityPreference : contient
```

## Weather service classes

```mermaid
classDiagram
class WeatherSnapshot {
  +UUID id
  +String cityCode
  +Instant fetchedAt
}
class WeatherReading {
  +Double temperatureC
  +Double windKmh
  +Double humidityPct
}
WeatherSnapshot "1" --> "1" WeatherReading : contient
```

## Sequence recommandation

```mermaid
sequenceDiagram
 actor U as Utilisateur
 participant FE as Frontend
 participant GW as Gateway
 participant A as Activity
 participant F as Forecast
 participant W as Weather
 participant AQ as AirQuality
 participant US as User
 U->>FE: Ville, activité et durée
 FE->>GW: POST /api/activities/recommendations + JWT
 GW->>A: Requête routée
 par Prévisions
  A->>F: GET /api/forecasts/{city}
  F-->>A: Version et heures
 and Météo courante
  A->>W: GET /api/weather/{city}
  W-->>A: Conditions et provenance
 and Qualité de l’air
  A->>AQ: GET /api/air-quality/{city}
  AQ-->>A: Indice et échelle
 end
 opt Personnalisation autorisée
  A->>US: Préférences de l’utilisateur autorisé
  US-->>A: Profil et villes favorites
 end
 A->>A: Vérifier fraîcheur et fenêtres continues
 A-->>GW: Classement et explications ou état incomplet
 GW-->>FE: Réponse JSON
 FE-->>U: Créneaux expliqués
```

## Sequence vector search

```mermaid
sequenceDiagram
 actor U as Utilisateur
 participant G as Gateway
 participant A as Activity
 participant Q as Qdrant
 participant W as Weather/Forecast/AirQuality
 U->>G: POST /api/activities/search (texte + contraintes)
 G->>A: JWT validé, requête
 A->>A: Embedding (modèle/version figés)
 A->>Q: top-k + filtre catalogue
 Q-->>A: candidats et similarité sémantique
 A->>W: OpenFeign (ville, plage temporelle)
 W-->>A: données datées et provenance
 A->>A: contraintes numériques puis classement expliqué
 A-->>U: résultats ou 503 si données indispensables absentes
```

## Sequence evenements

```mermaid
sequenceDiagram
 participant F as Forecast
 participant DB as forecast_db
 participant Q as RabbitMQ
 participant A as Alert
 participant AD as alert_db
 participant U as User
 participant H as DataHub
 F->>DB: Prévu : Mongo replica set, version + outbox
 F->>Q: ForecastUpdated(eventId)
 Q-->>F: Publisher confirm
 par File Alert
  Q->>A: Nouvelle version
  A->>AD: Vérifier inbox et calculer règle
  A->>U: Lire consentement autorisé
  U-->>A: Préférence actuelle
  A->>AD: Prévu : écriture atomique Redis alerte + clé inbox
  A-->>Q: ACK après écriture
 and File DataHub
  Q->>H: Copie de la nouvelle version
  H->>H: ClickHouse, eventId stable et lecture dédupliquée
  H-->>Q: ACK après écriture
 end
 Note over F,H: Conception après validation : livraison au moins une fois, idempotence et reprise à développer
 Note over Q,H: Retry borné puis quarantaine, rejeu avec eventId stable
```

