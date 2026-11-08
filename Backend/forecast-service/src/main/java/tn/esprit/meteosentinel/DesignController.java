package tn.esprit.meteosentinel;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController
public class DesignController {
 @GetMapping("/api/forecasts/design") public Map<String,Object> design(){return Map.of("service","forecast-service","owner","Dhia Borzli","database","forecast_db","databaseType","MongoDB","stage","VALIDATION_1_SKELETON","businessImplemented",false,"databaseConnected",false);}
 @RequestMapping(value={"/api/forecasts","/api/forecasts/**"})
 public ResponseEntity<ProblemDetail> planned(){var p=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED,"Contrat de conception : logique métier après validation enseignant.");p.setTitle("Fonctionnalité planifiée");return ResponseEntity.status(501).body(p);}
}
