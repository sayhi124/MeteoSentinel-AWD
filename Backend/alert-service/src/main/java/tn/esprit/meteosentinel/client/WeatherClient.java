package tn.esprit.meteosentinel.client;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/** Contrat initial de découverte ; appels métier ajoutés après validation. */
@FeignClient(name="weather-service") public interface WeatherClient {
 @GetMapping("/api/weather/design") Map<String,Object> design();
}
