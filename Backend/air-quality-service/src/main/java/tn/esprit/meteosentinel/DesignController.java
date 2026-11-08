package tn.esprit.meteosentinel;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController
public class DesignController {
 @GetMapping("/api/air-quality/design") public Map<String,Object> design(){return Map.of("service","air-quality-service","owner","Kaouther Nalouti","database","air_quality_db","databaseType","MySQL","stage","VALIDATION_1_SKELETON","businessImplemented",false,"databaseConnected",false);}
 @RequestMapping(value={"/api/air-quality","/api/air-quality/**"})
 public ResponseEntity<ProblemDetail> planned(){var p=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED,"Contrat de conception : logique métier après validation enseignant.");p.setTitle("Fonctionnalité planifiée");return ResponseEntity.status(501).body(p);}
}
