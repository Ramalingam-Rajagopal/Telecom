package telecom_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import telecom_api.dto.OutageRequestDTO;
import telecom_api.dto.OutageResponseDTO;
import telecom_api.entity.Outage;
import telecom_api.exception.ResourceNotFoundException;
import telecom_api.repository.OutageRepository;
import telecom_api.mapper.OutageMapper;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutageService {

    private final OutageRepository outageRepository;

    public Outage saveOutage(Outage outage) {
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

    public OutageResponseDTO updateOutage(Long id, OutageRequestDTO dto) {
        Outage existingOutage = outageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Outage not found with id: " + id));

        existingOutage.setArea(dto.getArea());
        existingOutage.setDescription(dto.getDescription());
        existingOutage.setSeverity(dto.getSeverity());
        existingOutage.setStatus(dto.getStatus());

        Outage updatedOutage = outageRepository.save(existingOutage);

        return OutageMapper.toResponseDTO(updatedOutage);
    }
}