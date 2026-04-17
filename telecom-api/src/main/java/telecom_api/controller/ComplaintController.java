package telecom_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telecom_api.dto.ComplaintRequestDTO;
import telecom_api.dto.ComplaintResponseDTO;
import telecom_api.entity.Complaint;
import telecom_api.entity.User;
import telecom_api.mapper.ComplaintMapper;
import telecom_api.repository.UserRepository;
import telecom_api.service.ComplaintService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import telecom_api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final UserRepository userRepository;

    @PostMapping
    public ComplaintResponseDTO createComplaint(@Valid @RequestBody ComplaintRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Complaint complaint = Complaint.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .severity(dto.getSeverity())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        Complaint savedComplaint = complaintService.saveComplaint(complaint);

        return ComplaintMapper.toResponseDTO(savedComplaint);
    }

    @GetMapping
    public List<ComplaintResponseDTO> getAllComplaints() {
        return complaintService.getAllComplaints()
                .stream()
                .map(ComplaintMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ComplaintResponseDTO getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.getComplaintById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        return ComplaintMapper.toResponseDTO(complaint);
    }

    @PutMapping("/{id}")
    public ComplaintResponseDTO updateComplaint(@PathVariable Long id, @Valid @RequestBody ComplaintRequestDTO dto) {
        return complaintService.updateComplaint(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return "Complaint deleted successfully";
    }
}