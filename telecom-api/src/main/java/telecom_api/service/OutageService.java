package telecom_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import telecom_api.dto.OutageRequestDTO;
import telecom_api.dto.OutageResponseDTO;
import telecom_api.dto.OutageUpdateDTO;
import telecom_api.entity.Outage;
import telecom_api.enums.OutageStatus;
import telecom_api.exception.ResourceNotFoundException;
import telecom_api.repository.OutageRepository;
import telecom_api.mapper.OutageMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import telecom_api.entity.User;
import telecom_api.repository.UserRepository;
import telecom_api.enums.Role;

@Service
@RequiredArgsConstructor
public class OutageService {

    private final OutageRepository outageRepository;
    private final UserRepository userRepository;
    private final OutageMapper outageMapper;

    public Outage createOutage(OutageRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    // 2. Build outage
        Outage outage = Outage.builder()
            .area(dto.getArea())
            .description(dto.getDescription())
            .severity(dto.getSeverity())
            .status(OutageStatus.REPORTED)
            .reportedBy(user)
            .build();

    return outageRepository.save(outage);
    }

    public List<Outage> getAllOutages() {
        return outageRepository.findAll();
    }

    public Optional<Outage> getOutageById(Long id) {
        return outageRepository.findById(id);
    }

    public void deleteOutage(Long id) {
        Outage outage = outageRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Outage not found with id: " + id));

        outageRepository.delete(outage);
    }

    public OutageResponseDTO updateOutage(Long id, OutageUpdateDTO dto) {
        Outage existingOutage = outageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Outage not found with id: " + id));

        if (dto.getArea() != null) {
            existingOutage.setArea(dto.getArea());
        }

        if (dto.getDescription() != null) {
            existingOutage.setDescription(dto.getDescription());
        }

        if (dto.getSeverity() != null) {
            existingOutage.setSeverity(dto.getSeverity());
        }

        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingOutage.setLastUpdatedBy(user);

        Outage updatedOutage = outageRepository.save(existingOutage);

        return outageMapper.toResponseDTO(updatedOutage);
    }

    public Outage updateStatus(Long outageId, Long adminId, OutageStatus status) {

        Outage outage = outageRepository.findById(outageId)
            .orElseThrow(() -> new ResourceNotFoundException("Outage not found"));

        User admin = userRepository.findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can update status");
        }

        if (outage.getStatus() == OutageStatus.REPORTED && status == OutageStatus.RESOLVED) {
            throw new RuntimeException("Must move to IN_PROGRESS before RESOLVED");
        }
        
        outage.setStatus(status);
        outage.setLastUpdatedBy(admin);

        if (status == OutageStatus.RESOLVED) {
            outage.setResolvedBy(admin);
            outage.setResolvedAt(LocalDateTime.now());
        }
        


        return outageRepository.save(outage);
}
}