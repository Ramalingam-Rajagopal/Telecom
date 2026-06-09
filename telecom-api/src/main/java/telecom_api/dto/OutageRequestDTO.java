package telecom_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import telecom_api.enums.SeverityLevel;

@Data
public class OutageRequestDTO {

    @NotBlank(message = "Area is required")
    private String area;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Severity is required")
    private SeverityLevel severity;

}