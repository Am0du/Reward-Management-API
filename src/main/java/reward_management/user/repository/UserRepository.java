package reward_management.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reward_management.user.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
