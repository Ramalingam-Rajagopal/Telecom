package telecom_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_api.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}