package telecom_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import telecom_api.dto.ComplaintRequestDTO;
import telecom_api.dto.ComplaintResponseDTO;
import telecom_api.entity.Complaint;
import telecom_api.enums.ComplaintStatus;
import telecom_api.exception.ResourceNotFoundException;
import telecom_api.repository.ComplaintRepository;
import telecom_api.mapper.ComplaintMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import telecom_api.entity.User;
import telecom_api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public Complaint saveComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public void deleteComplaint(Long id) {
        Complaint complaint = complaintRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        complaintRepository.delete(complaint);
    }

    public ComplaintResponseDTO updateComplaint(Long id, ComplaintRequestDTO dto) {
        Complaint existingComplaint = complaintRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));

        if (dto.getTitle() != null) {
            existingComplaint.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            existingComplaint.setDescription(dto.getDescription());
        }

        if (dto.getSeverity() != null) {
            existingComplaint.setSeverity(dto.getSeverity());
        }
        Complaint updatedComplaint = complaintRepository.save(existingComplaint);

        return ComplaintMapper.toResponseDTO(updatedComplaint);
    }

    public Complaint updateStatus(Long complaintId, ComplaintStatus status) {

        Complaint complaint = complaintRepository.findById(complaintId)
            .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User admin = userRepository.findByEmail(email)
        .orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        if (complaint.getStatus() == ComplaintStatus.OPEN && status == ComplaintStatus.RESOLVED) {

            throw new RuntimeException("Must move to IN_PROGRESS before RESOLVED");
        }

        complaint.setStatus(status);
        complaint.setLastUpdatedBy(admin);

        if (status == ComplaintStatus.RESOLVED) {
            complaint.setResolvedBy(admin);
            complaint.setResolvedAt(LocalDateTime.now());
        }

        return complaintRepository.save(complaint);
    }
}