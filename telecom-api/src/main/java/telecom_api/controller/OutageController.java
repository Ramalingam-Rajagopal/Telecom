package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.OutageRequestDTO;
import telecom_api.dto.OutageResponseDTO;
import telecom_api.entity.Outage;
import telecom_api.mapper.OutageMapper;
import telecom_api.service.OutageService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import telecom_api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/outages")
@RequiredArgsConstructor
public class OutageController {

    private final OutageService outageService;

    @PostMapping
    public OutageResponseDTO createOutage(@Valid @RequestBody OutageRequestDTO dto) {

        Outage outage = Outage.builder()
                .area(dto.getArea())
                .description(dto.getDescription())
                .severity(dto.getSeverity())
                .status(dto.getStatus())
                .reportedAt(LocalDateTime.now())
                .build();

        Outage savedOutage = outageService.saveOutage(outage);

        return OutageMapper.toResponseDTO(savedOutage);
    }

    @GetMapping
    public List<OutageResponseDTO> getAllOutages() {
        return outageService.getAllOutages()
                .stream()
                .map(OutageMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OutageResponseDTO getOutageById(@PathVariable Long id) {
        Outage outage = outageService.getOutageById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Outage not found"));

        return OutageMapper.toResponseDTO(outage);
    }

    @DeleteMapping("/{id}")
    public String deleteOutage(@PathVariable Long id) {
        outageService.deleteOutage(id);
        return "Outage deleted successfully";
    }
}