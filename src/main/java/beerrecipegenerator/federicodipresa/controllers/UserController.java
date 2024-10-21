package beerrecipegenerator.federicodipresa.controllers;


import beerrecipegenerator.federicodipresa.entities.User;
import beerrecipegenerator.federicodipresa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("Ricevuta richiesta di registrazione per: " + user.getEmail());
        try {
            // Verifica se l'utente esiste già
            if (userService.findByEmail(user.getEmail()) != null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Un utente con questa email esiste già");
            }

            // Salva il nuovo utente
            User savedUser = userService.saveUser(user);

            // Per sicurezza, impostiamo la password a null prima di inviarla nella risposta
            savedUser.setPassword(null);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la registrazione: " + e.getMessage());
        }
    }

    // Endpoint per verificare se un utente esiste (utile per il login)
    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            // Per sicurezza, impostiamo la password a null prima di inviarla nella risposta
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utente non trovato");
        }
    }

    // Endpoint per il login (opzionale, se lo vuoi implementare)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Per sicurezza, impostiamo la password a null prima di inviarla nella risposta
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email o password non validi");
        }
    }
}