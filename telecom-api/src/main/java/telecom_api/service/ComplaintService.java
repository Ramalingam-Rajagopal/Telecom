package telecom_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import telecom_api.dto.ComplaintRequestDTO;
import telecom_api.dto.ComplaintResponseDTO;
import telecom_api.entity.Complaint;
import telecom_api.exception.ResourceNotFoundException;
import telecom_api.repository.ComplaintRepository;
import telecom_api.mapper.ComplaintMapper;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

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

        existingComplaint.setTitle(dto.getTitle());
        existingComplaint.setDescription(dto.getDescription());
        existingComplaint.setStatus(dto.getStatus());
        existingComplaint.setSeverity(dto.getSeverity());

        Complaint updatedComplaint = complaintRepository.save(existingComplaint);

        return ComplaintMapper.toResponseDTO(updatedComplaint);
    }
}