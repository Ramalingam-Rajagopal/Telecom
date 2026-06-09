package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.ComplaintRequestDTO;
import telecom_api.dto.ComplaintResponseDTO;
import telecom_api.entity.Complaint;
import telecom_api.entity.User;
import telecom_api.enums.ComplaintStatus;
import telecom_api.mapper.ComplaintMapper;
import telecom_api.repository.UserRepository;
import telecom_api.service.ComplaintService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import telecom_api.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ComplaintResponseDTO> createComplaint(@Valid @RequestBody ComplaintRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Complaint complaint = Complaint.builder().title(dto.getTitle()).description(dto.getDescription()).status(dto.getStatus()).severity(dto.getSeverity()).user(user).build();

        Complaint savedComplaint = complaintService.saveComplaint(complaint);

        return ResponseEntity.status(HttpStatus.CREATED).body(ComplaintMapper.toResponseDTO(savedComplaint));
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponseDTO>> getAllComplaints() {
        List<ComplaintResponseDTO> complaints = complaintService.getAllComplaints().stream().map(ComplaintMapper::toResponseDTO).collect(Collectors.toList());

        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponseDTO> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id).orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        return ResponseEntity.ok(ComplaintMapper.toResponseDTO(complaint));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplaintResponseDTO> updateComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintRequestDTO dto) {
        return ResponseEntity.ok(complaintService.updateComplaint(id, dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ComplaintResponseDTO> updateStatus(
        @PathVariable Long id,
        @RequestParam ComplaintStatus status) {

    Complaint complaint = complaintService.updateStatus(id, status);

    return ResponseEntity.ok(ComplaintMapper.toResponseDTO(complaint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
}