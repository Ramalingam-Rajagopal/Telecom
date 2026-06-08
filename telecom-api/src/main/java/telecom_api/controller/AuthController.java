package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.LoginRequestDTO;
import telecom_api.dto.LoginResponseDTO;
import telecom_api.service.AuthService;
import telecom_api.entity.User;
import telecom_api.security.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        User user = authService.authenticate(request);

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}