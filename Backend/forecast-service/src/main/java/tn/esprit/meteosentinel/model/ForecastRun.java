package tn.esprit.meteosentinel.model;
import java.util.UUID;
import java.time.*;
/** Modèle de conception ; non persisté avant validation du métier. */
public record ForecastRun(UUID id, String cityCode, Instant issuedAt) {}
