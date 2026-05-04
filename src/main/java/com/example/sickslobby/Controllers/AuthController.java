package com.example.sickslobby.Controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final FirebaseAuth firebaseAuth;

    public AuthController(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();

        if (token == null || !token.startsWith("Bearer ")) {
            response.put("valid", false);
            response.put("error", "Token no proporcionado o formato invalido");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            String idToken = token.substring(7);
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);

            response.put("valid", true);
            response.put("uid", decodedToken.getUid());
            response.put("email", decodedToken.getEmail());
            response.put("name", decodedToken.getName());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FirebaseAuthException e) {
            response.put("valid", false);
            response.put("error", "Token invalido: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> listUsers() {
        Map<String, Object> response = new HashMap<>();

        try {
            var users = firebaseAuth.listUsers(null);
            var userList = new java.util.ArrayList<Map<String, Object>>();

            while (users.hasNext()) {
                var user = users.next();
                var userData = new HashMap<String, Object>();
                userData.put("uid", user.getUid());
                userData.put("email", user.getEmail());
                userData.put("displayName", user.getDisplayName());
                userData.put("phoneNumber", user.getPhoneNumber());
                userList.add(userData);
            }

            response.put("users", userList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (FirebaseAuthException e) {
            response.put("error", "Error al listar usuarios: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
