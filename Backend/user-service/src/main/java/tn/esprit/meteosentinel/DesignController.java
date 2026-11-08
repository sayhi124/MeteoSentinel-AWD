package tn.esprit.meteosentinel;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController
public class DesignController {
 @GetMapping("/api/users/design") public Map<String,Object> design(){return Map.of("service","user-service","owner","Ahmed Ben Mahmoud","database","user_db","databaseType","MariaDB","stage","VALIDATION_1_SKELETON","businessImplemented",false,"databaseConnected",false);}
 @RequestMapping(value={"/api/users","/api/users/**"})
 public ResponseEntity<ProblemDetail> planned(){var p=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED,"Contrat de conception : logique métier après validation enseignant.");p.setTitle("Fonctionnalité planifiée");return ResponseEntity.status(501).body(p);}
}
