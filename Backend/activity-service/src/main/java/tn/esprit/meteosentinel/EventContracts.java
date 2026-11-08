package tn.esprit.meteosentinel;
import java.time.Instant;
import java.util.UUID;
/** Contrat d'événement uniquement : pas de publication avant validation. */
public final class EventContracts {
 private EventContracts(){}
 public record Envelope(UUID eventId,String eventType,int schemaVersion,String producer,Instant occurredAt,String aggregateId,String correlationId,String payloadJson){}
}
