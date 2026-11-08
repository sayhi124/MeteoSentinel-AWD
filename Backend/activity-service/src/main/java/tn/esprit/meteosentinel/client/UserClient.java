package tn.esprit.meteosentinel.client;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/** Contrat initial de découverte ; appels métier ajoutés après validation. */
@FeignClient(name="user-service") public interface UserClient {
 @GetMapping("/api/users/design") Map<String,Object> design();
}
