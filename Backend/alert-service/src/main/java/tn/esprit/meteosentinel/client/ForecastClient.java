package tn.esprit.meteosentinel.client;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/** Contrat initial de découverte ; appels métier ajoutés après validation. */
@FeignClient(name="forecast-service") public interface ForecastClient {
 @GetMapping("/api/forecasts/design") Map<String,Object> design();
}
