# Sujet et périmètre

MétéoSentinel aide un utilisateur à choisir une activité et un créneau à l’échelle d’une ville en croisant météo, prévisions, qualité de l’air et préférences. Un sportif, une association ou un étudiant compare des créneaux expliqués. Le système ne remplace pas une vigilance officielle et ne calcule pas un itinéraire.

## User stories et critères de recette

### US-1 — Visiteur

Je souhaite consulter la météo et l’AQI d’une ville.

Recette : la source, l’heure et l’échelle AQI sont visibles ; indisponible ne vaut pas zéro.

### US-2 — Utilisateur

Je souhaite chercher une activité selon mes centres d’intérêt.

Recette : la similarité sémantique a un modèle/version documentés et les raisons sont affichées.

### US-3 — Utilisateur

Je souhaite comparer des créneaux de 1 à 4 heures.

Recette : aucune fenêtre ne traverse une heure manquante ; seuils météo explicités.

### US-4 — Utilisateur

Je souhaite gérer préférences et consentement.

Recette : le serveur lie /me à mon identité ; le retrait cesse les futures notifications.

### US-5 — Utilisateur

Je souhaite comprendre une alerte.

Recette : classe, facteurs dominants, version de règle et date des données sont visibles.

### US-6 — Administrateur

Je souhaite contrôler qualité et erreurs des prévisions.

Recette : les références sont typées observation/réanalyse et les métriques séparées par horizon.

## Périmètre réaliste

MVP après validation : trois villes tunisiennes, météo actuelle, 48 heures de prévision, un indice AQI documenté, catalogue limité, trois activités et alertes dans l’application. Hors périmètre initial : SMS, routage GPS, entraînement de modèle ML, promesse sanitaire. La recherche vectorielle utilise un modèle existant figé ; l’évaluation du modèle et son ingestion restent à développer.
