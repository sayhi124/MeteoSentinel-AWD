package tn.esprit.meteosentinel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DesignControllerTest {
 @Test void explicitlyDeclaresDesignOnly() {var d=new DesignController().design();assertEquals("weather-service",d.get("service"));assertEquals(false,d.get("businessImplemented"));}
 @Test void plannedBusinessReturns501() {assertEquals(501,new DesignController().planned().getStatusCode().value());}
}
