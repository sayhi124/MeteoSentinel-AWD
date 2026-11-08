package tn.esprit.meteosentinel.model;
import java.util.UUID;
import java.time.*;
/** Modèle de conception ; non persisté avant validation du métier. */
public record AirQualityIndex(Double pm25, Double pm10, String indexType) {}
