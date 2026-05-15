package telecom_api.dto;

import lombok.Builder;
import lombok.Data;
import telecom_api.enums.ComplaintStatus;
import telecom_api.enums.SeverityLevel;

import java.time.LocalDateTime;

@Data
@Builder
public class ComplaintResponseDTO {

    private Long id;
    private String title;
    private String description;
    private ComplaintStatus status;
    private SeverityLevel severity;
    private LocalDateTime createdAt;
    private Long userId;
    private LocalDateTime resolvedAt;
    private Long resolvedById;
    private Long lastUpdatedById;
}