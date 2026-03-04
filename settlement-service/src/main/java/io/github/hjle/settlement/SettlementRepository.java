package io.github.hjle.settlement;

import io.github.hjle.settlement.dto.SettlementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<SettlementEntity, Long> {

    java.util.Optional<SettlementEntity> findByOrderId(Long orderId);

    java.util.List<SettlementEntity> findByUserId(String userId);

    boolean existsByOrderId(Long orderId);
}
