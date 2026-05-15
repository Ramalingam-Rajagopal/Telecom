package telecom_api.dto;

import lombok.Data;
import telecom_api.enums.SeverityLevel;

@Data
public class ComplaintUpdateDTO {

    private String title;
    private String description;
    private SeverityLevel severity;

    private Long userId;
}