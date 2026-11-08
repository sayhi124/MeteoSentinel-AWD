package tn.esprit.meteosentinel;
import org.springframework.context.annotation.*;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.beans.factory.annotation.Value;
@Configuration
public class GatewaySecurity {
 @Bean @Profile("!secure") SecurityWebFilterChain local(ServerHttpSecurity h){return h.csrf(c->c.disable()).authorizeExchange(a->a.anyExchange().permitAll()).build();}
 @Bean @Profile("secure") SecurityWebFilterChain secure(ServerHttpSecurity h){return h.csrf(c->c.disable()).authorizeExchange(a->a.pathMatchers("/","/index.html","/assets/**","/services.json","/docs/**","/weather-city-3d.png","/actuator/health").permitAll().anyExchange().authenticated()).oauth2ResourceServer(o->o.jwt(j->{})).build();}
 @Bean @Profile("secure") ReactiveJwtDecoder decoder(@Value("${security.issuer}")String issuer,@Value("${security.jwk-set-uri}")String jwks){
  var d=NimbusReactiveJwtDecoder.withJwkSetUri(jwks).build();
  OAuth2TokenValidator<Jwt> aud=jwt->jwt.getAudience().contains("meteosentinel-api")?OAuth2TokenValidatorResult.success():OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token","Audience invalide",null));
  d.setJwtValidator(new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefaultWithIssuer(issuer),aud));return d;
 }
}
