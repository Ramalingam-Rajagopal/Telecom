package telecom_api.dto;

import lombok.Builder;
import lombok.Data;
import telecom_api.enums.OutageStatus;
import telecom_api.enums.SeverityLevel;

import java.time.LocalDateTime;

@Data
@Builder
public class OutageResponseDTO {

    private Long id;
    private String area;
    private String description;
    private SeverityLevel severity;
    private OutageStatus status;
    private LocalDateTime reportedAt;
    private LocalDateTime resolvedAt;
    private Long reportedById;
    private Long resolvedById;
}