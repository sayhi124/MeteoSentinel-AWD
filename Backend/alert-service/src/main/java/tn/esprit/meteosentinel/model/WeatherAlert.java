package tn.esprit.meteosentinel.model;
import java.util.UUID;
public record WeatherAlert(UUID id,AlertLevel level,AlertStatus status){}
