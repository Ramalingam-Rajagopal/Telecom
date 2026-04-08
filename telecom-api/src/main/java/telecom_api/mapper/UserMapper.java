package telecom_api.mapper;

import telecom_api.dto.UserRequestDTO;
import telecom_api.dto.UserResponseDTO;
import telecom_api.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}