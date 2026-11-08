package tn.esprit.meteosentinel;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController
public class DesignController {
 @GetMapping("/api/activities/design") public Map<String,Object> design(){return Map.of("service","activity-service","owner","Mohamed Aziz Sayhi","database","activity_db","databaseType","Qdrant (vectoriel)","stage","VALIDATION_1_SKELETON","businessImplemented",false,"databaseConnected",false);}
 @RequestMapping(value={"/api/activities","/api/activities/**"})
 public ResponseEntity<ProblemDetail> planned(){var p=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED,"Contrat de conception : logique métier après validation enseignant.");p.setTitle("Fonctionnalité planifiée");return ResponseEntity.status(501).body(p);}
}
