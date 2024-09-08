package reward_management.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reward_management.management.entity.CashbackHistory;

import java.util.UUID;

public interface CashBackHistoryRepository extends JpaRepository<CashbackHistory, Integer> {

    @Query("SELECT c FROM CashbackHistory c JOIN c.user u WHERE u.id = :userId")
    Page<CashbackHistory> findByUserId(@Param("userId") UUID userId, Pageable pageable);
}
