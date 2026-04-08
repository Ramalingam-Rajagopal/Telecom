package telecom_api.mapper;

import telecom_api.dto.OutageResponseDTO;
import telecom_api.entity.Outage;

public class OutageMapper {

    public static OutageResponseDTO toResponseDTO(Outage outage) {
        return OutageResponseDTO.builder()
                .id(outage.getId())
                .area(outage.getArea())
                .description(outage.getDescription())
                .severity(outage.getSeverity())
                .status(outage.getStatus())
                .reportedAt(outage.getReportedAt())
                .build();
    }
}