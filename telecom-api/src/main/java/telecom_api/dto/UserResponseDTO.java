package telecom_api.dto;

import lombok.Builder;
import lombok.Data;
import telecom_api.enums.Role;

@Data
@Builder
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
}