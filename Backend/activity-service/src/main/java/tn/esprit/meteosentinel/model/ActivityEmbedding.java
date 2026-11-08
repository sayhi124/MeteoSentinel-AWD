package tn.esprit.meteosentinel.model;
import java.util.List;
import java.util.UUID;
/** Contrat de conception Qdrant : la similarité ne mesure pas la sécurité météo. */
public record ActivityEmbedding(UUID id, String activityCode, String description,
 String modelVersion, List<Float> vector, List<String> tags) {}
