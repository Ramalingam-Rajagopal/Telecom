package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.LoginRequestDTO;
import telecom_api.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDTO request) {

        authService.authenticate(request);

        return ResponseEntity.ok("Login Successful");
    }
}