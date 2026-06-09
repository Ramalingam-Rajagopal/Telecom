package telecom_api.dto;


import lombok.Data;
import telecom_api.enums.SeverityLevel;

@Data
public class OutageUpdateDTO {

    private String area;
    private String description;
    private SeverityLevel severity;
}
