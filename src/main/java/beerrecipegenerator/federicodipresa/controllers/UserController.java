package beerrecipegenerator.federicodipresa.controllers;

import beerrecipegenerator.federicodipresa.JWT.JwtUtil;
import beerrecipegenerator.federicodipresa.entities.User;
import beerrecipegenerator.federicodipresa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("Ricevuta richiesta di registrazione per: " + user.getEmail());
        try {
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Un utente con questa email esiste già");
            }

            User savedUser = userService.saveUser(user);
            String token = jwtUtil.generateToken(savedUser.getEmail());

            // Modifica: invia solo email e token
            Map<String, Object> response = new HashMap<>();
            response.put("email", savedUser.getEmail());
            response.put("token", token);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la registrazione: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            User user = userService.findByEmail(loginRequest.getEmail());

            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                // Genera il token JWT
                String token = jwtUtil.generateToken(user.getEmail());

                // Modifica: invia solo email e token
                Map<String, Object> response = new HashMap<>();
                response.put("email", user.getEmail());
                response.put("token", token);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Email o password non validi");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il login: " + e.getMessage());
        }
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(
            @PathVariable String email,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Verifica il token JWT
            String token = authHeader.substring(7);
            String userEmail = jwtUtil.extractUsername(token);

            if (!email.equals(userEmail)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Non sei autorizzato ad accedere a questi dati");
            }

            User user = userService.findByEmail(email);
            if (user != null) {
                user.setPassword(null);

                // Modifica: invia solo le informazioni necessarie
                Map<String, Object> response = new HashMap<>();
                response.put("email", user.getEmail());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Utente non trovato");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token non valido o scaduto");
        }
    }
}