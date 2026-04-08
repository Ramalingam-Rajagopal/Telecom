package telecom_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_api.entity.Outage;

public interface OutageRepository extends JpaRepository<Outage, Long> {
}