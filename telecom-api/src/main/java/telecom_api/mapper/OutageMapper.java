package telecom_api.mapper;

import telecom_api.dto.OutageResponseDTO;
import telecom_api.entity.Outage;
import org.springframework.stereotype.Component;

@Component
public class OutageMapper {

    public OutageResponseDTO toResponseDTO(Outage outage) {
        return OutageResponseDTO.builder()
                .id(outage.getId())
                .area(outage.getArea())
                .description(outage.getDescription())
                .severity(outage.getSeverity())
                .status(outage.getStatus())
                .reportedAt(outage.getReportedAt())
                .resolvedAt(outage.getResolvedAt())
                .reportedById(outage.getReportedBy() != null ? outage.getReportedBy().getId() : null)
                .resolvedById(outage.getResolvedBy() != null ? outage.getResolvedBy().getId() : null)
                .build();
    }
}