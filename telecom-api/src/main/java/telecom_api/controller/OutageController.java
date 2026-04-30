package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.OutageRequestDTO;
import telecom_api.dto.OutageResponseDTO;
import telecom_api.dto.OutageUpdateDTO;
import telecom_api.entity.Outage;
import telecom_api.enums.OutageStatus;
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
    private final OutageMapper outageMapper;

    @PostMapping
    public ResponseEntity<OutageResponseDTO> createOutage(@Valid @RequestBody OutageRequestDTO dto) {

        Outage outage = outageService.createOutage(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(outageMapper.toResponseDTO(outage));
    }

    @GetMapping
    public ResponseEntity<List<OutageResponseDTO>> getAllOutages() {
        List<OutageResponseDTO> outages = outageService.getAllOutages().stream().map(outageMapper::toResponseDTO).collect(Collectors.toList());

        return ResponseEntity.ok(outages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutageResponseDTO> getOutageById(@PathVariable Long id) {
        Outage outage = outageService.getOutageById(id).orElseThrow(() -> new ResourceNotFoundException("Outage not found"));

        return ResponseEntity.ok(outageMapper.toResponseDTO(outage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutageResponseDTO> updateOutage(@PathVariable Long id, @RequestBody OutageUpdateDTO dto) {
        return ResponseEntity.ok(outageService.updateOutage(id, dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OutageResponseDTO> updateStatus(
        @PathVariable Long id,
        @RequestParam Long adminId,
        @RequestParam OutageStatus status) {

        Outage outage = outageService.updateStatus(id, adminId, status);

        return ResponseEntity.ok(outageMapper.toResponseDTO(outage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutage(@PathVariable Long id) {
        outageService.deleteOutage(id);
        return ResponseEntity.noContent().build();
    }
}