package telecom_api.mapper;

import telecom_api.dto.ComplaintResponseDTO;
import telecom_api.entity.Complaint;

public class ComplaintMapper {

    public static ComplaintResponseDTO toResponseDTO(Complaint complaint) {
        return ComplaintResponseDTO.builder()
                .id(complaint.getId())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .status(complaint.getStatus())
                .severity(complaint.getSeverity())
                .createdAt(complaint.getCreatedAt())
                .userId(complaint.getUser().getId())
                .build();
    }
}