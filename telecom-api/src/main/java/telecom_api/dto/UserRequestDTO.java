package telecom_api.dto;

import lombok.Data;
import telecom_api.enums.Role;

@Data
public class UserRequestDTO {

    private String fullName;
    private String email;
    private String password;
    private Role role;
}