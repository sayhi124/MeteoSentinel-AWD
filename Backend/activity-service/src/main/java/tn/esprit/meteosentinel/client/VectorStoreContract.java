package tn.esprit.meteosentinel.client;
import java.util.List;
import java.util.UUID;
/** Port métier prévu. Aucun adaptateur ni embedding inventé dans le squelette S4. */
public interface VectorStoreContract {
 record Candidate(UUID id, String activityCode, double similarity, String embeddingVersion) {}
 List<Candidate> search(List<Float> vector, List<String> tags, int limit);
}
