package telecom_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import telecom_api.enums.ComplaintStatus;
import telecom_api.enums.SeverityLevel;

@Data
public class ComplaintRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private ComplaintStatus status;

    @NotNull(message = "Severity is required")
    private SeverityLevel severity;

}