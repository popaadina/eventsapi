package com.app.eventsmanagementapi.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@WebFilter(filterName = "CustomAuthorizationFilter", urlPatterns = "/*")
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("A intrat in CustomAuthorizationFilter");
        if(request.getServletPath().equals("/api/auth/login") ||
                request.getServletPath().equals("/api/auth/token/refresh") ||
                request.getServletPath().equals("/api/auth/signup")) {
            filterChain.doFilter(request, response);
        } else {

            // Daca incearca sa acceseze oricare alt endpoint decat cele din if atunci trebuie
            // sa verificam token-ul: prezenta acestuia in header si mai apoi validitatea prin
            // verificatorul JWTVerifier

            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("parolaInClar.ro".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    // In cazul in care vrei sa verifici in functie de rol daca are acces.
                    String[] roles = decodedJWT.getClaim("roluri").asArray(String.class);
                    filterChain.doFilter(request, response);
                }catch (Exception exception) {
                    log.error("Error logging in: {}", exception);
//                    response.setHeader("error", exception.getMessage());
                    response.setStatus(UNAUTHORIZED.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.setStatus(401);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                log.error("Error nu exista headerul cu Bearer");
                response.setStatus(UNAUTHORIZED.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", "Nu aveti token-ul in headerul de Authorization");
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setStatus(401);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
    }

}
