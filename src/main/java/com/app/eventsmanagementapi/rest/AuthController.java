package com.app.eventsmanagementapi.rest;

import com.app.eventsmanagementapi.dto.LoginRequest;
import com.app.eventsmanagementapi.dto.RegisterRequest;
import com.app.eventsmanagementapi.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest, HttpServletResponse response, HttpServletRequest request) throws IOException {

        authService.signup(registerRequest);
        String url = request.getRequestURL().toString();
        String username = registerRequest.getUsername();
        String rol = "USER";
        Map<String, String> tokens = getJwtTokens(username, url, rol);
        return ResponseEntity.ok().body(tokens);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        if(authService.login(loginRequest)) {
            String url = request.getRequestURL().toString();
            String username = loginRequest.getUsername();
            String rol = "USER";
            Map<String, String> tokens = getJwtTokens(username, url, rol);
            return ResponseEntity.ok().body(tokens);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity loginAdmin(@RequestBody LoginRequest loginRequest) {
        if(authService.loginAdmin(loginRequest)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private Map<String, String> getJwtTokens(String username, String requestURL, String rol){

        Algorithm algorithm = Algorithm.HMAC256("parolaInClar.ro".getBytes());
        String access_token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 2000))
                .withIssuer(requestURL)
                .withClaim("roluri", rol)
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject("username")
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(requestURL)
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        return tokens;
    }
}
