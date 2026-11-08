package tn.esprit.meteosentinel;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController
public class DesignController {
 @GetMapping("/api/weather/design") public Map<String,Object> design(){return Map.of("service","weather-service","owner","Nour Maroufi","database","weather_db","databaseType","PostgreSQL","stage","VALIDATION_1_SKELETON","businessImplemented",false,"databaseConnected",false);}
 @RequestMapping(value={"/api/weather","/api/weather/**"})
 public ResponseEntity<ProblemDetail> planned(){var p=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED,"Contrat de conception : logique métier après validation enseignant.");p.setTitle("Fonctionnalité planifiée");return ResponseEntity.status(501).body(p);}
}
