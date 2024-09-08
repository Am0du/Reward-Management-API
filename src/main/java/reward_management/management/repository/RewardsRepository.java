package reward_management.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reward_management.management.entity.Reward;

import java.util.Optional;
import java.util.UUID;

public interface RewardsRepository extends JpaRepository<Reward, Integer> {

    @Query("SELECT r FROM Reward r JOIN r.user u WHERE u.id = :userId")
    Optional<Reward> findByUserId(@Param("userId") UUID userId);
}
