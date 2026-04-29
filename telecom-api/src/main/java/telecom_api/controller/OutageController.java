package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.OutageRequestDTO;
import telecom_api.dto.OutageResponseDTO;
import telecom_api.entity.Outage;
import telecom_api.mapper.OutageMapper;
import telecom_api.service.OutageService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import telecom_api.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/outages")
@RequiredArgsConstructor
public class OutageController {

    private final OutageService outageService;

    @PostMapping
    public ResponseEntity<OutageResponseDTO> createOutage(@Valid @RequestBody OutageRequestDTO dto) {

        Outage outage = Outage.builder().area(dto.getArea()).description(dto.getDescription()).severity(dto.getSeverity()).status(dto.getStatus()).build();

        Outage savedOutage = outageService.saveOutage(outage);

        return ResponseEntity.status(HttpStatus.CREATED).body(OutageMapper.toResponseDTO(savedOutage));
    }

    @GetMapping
    public ResponseEntity<List<OutageResponseDTO>> getAllOutages() {
        List<OutageResponseDTO> outages = outageService.getAllOutages().stream().map(OutageMapper::toResponseDTO).collect(Collectors.toList());

        return ResponseEntity.ok(outages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutageResponseDTO> getOutageById(@PathVariable Long id) {
        Outage outage = outageService.getOutageById(id).orElseThrow(() -> new ResourceNotFoundException("Outage not found"));

        return ResponseEntity.ok(OutageMapper.toResponseDTO(outage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutageResponseDTO> updateOutage(@PathVariable Long id, @Valid @RequestBody OutageRequestDTO dto) {
        return ResponseEntity.ok(outageService.updateOutage(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutage(@PathVariable Long id) {
        outageService.deleteOutage(id);
        return ResponseEntity.noContent().build();
    }
}