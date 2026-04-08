package telecom_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_api.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}