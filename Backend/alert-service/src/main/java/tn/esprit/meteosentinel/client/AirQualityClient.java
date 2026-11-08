package tn.esprit.meteosentinel.client;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/** Contrat initial de découverte ; appels métier ajoutés après validation. */
@FeignClient(name="air-quality-service") public interface AirQualityClient {
 @GetMapping("/api/air-quality/design") Map<String,Object> design();
}
