package tn.esprit.meteosentinel.model;
import java.util.UUID;
public record QualityReport(UUID recordId,String checkCode,CheckResult result){}
