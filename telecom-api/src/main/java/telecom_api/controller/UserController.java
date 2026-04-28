package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.UserRequestDTO;
import telecom_api.dto.UserResponseDTO;
import telecom_api.entity.User;
import telecom_api.mapper.UserMapper;
import telecom_api.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import telecom_api.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponseDTO(savedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers().stream().map(UserMapper::toResponseDTO).collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(UserMapper.toResponseDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO>updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
    return ResponseEntity.ok(userService.updateUser(id, dto));
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}