package tn.esprit.meteosentinel;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.beans.factory.annotation.Value;
@Configuration
public class SecurityConfiguration {
 @Bean @Profile("!secure") SecurityFilterChain local(HttpSecurity http)throws Exception {return http.csrf(c->c.disable()).authorizeHttpRequests(a->a.anyRequest().permitAll()).build();}
 @Bean @Profile("secure") SecurityFilterChain secure(HttpSecurity http)throws Exception {return http.csrf(c->c.disable()).authorizeHttpRequests(a->a.requestMatchers("/actuator/health","/actuator/info").permitAll().anyRequest().authenticated()).oauth2ResourceServer(o->o.jwt(j->{})).build();}
 @Bean @Profile("secure") JwtDecoder decoder(@Value("${security.issuer}")String issuer,@Value("${security.jwk-set-uri}")String jwks){
  var d=NimbusJwtDecoder.withJwkSetUri(jwks).build();
  OAuth2TokenValidator<Jwt> audience=jwt->jwt.getAudience().contains("meteosentinel-api")?OAuth2TokenValidatorResult.success():OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token","Audience invalide",null));
  d.setJwtValidator(new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefaultWithIssuer(issuer),audience));return d;
 }
}
