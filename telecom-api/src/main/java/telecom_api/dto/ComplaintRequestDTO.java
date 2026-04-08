package telecom_api.dto;

import lombok.Data;
import telecom_api.enums.ComplaintStatus;
import telecom_api.enums.SeverityLevel;

@Data
public class ComplaintRequestDTO {

    private String title;
    private String description;
    private ComplaintStatus status;
    private SeverityLevel severity;
    private Long userId;
}