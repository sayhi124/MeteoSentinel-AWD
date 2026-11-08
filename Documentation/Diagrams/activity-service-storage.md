# Activity / Qdrant (vectoriel)

Collection activity_catalog : point UUID, vector[384] normalisé avec modèle paraphrase-multilingual-MiniLM-L12-v2 version figée, payload {activityCode,title,description,embeddingVersion,minAge?,tags}. Dimension/modèle à vérifier avant ingestion, toute migration utilise une nouvelle collection. Les résultats météo ne sont pas stockés comme vérité vectorielle.

Qdrant : recherche vectorielle filtrée sur catalogue d’activités. Le vecteur compare préférences/descriptions ; les contraintes météo/AQI sont contrôlées ensuite sur les données numériques. Aucun score vectoriel présenté comme une probabilité de sécurité.
