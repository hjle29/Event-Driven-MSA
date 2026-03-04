package io.github.hjle.settlement;

import com.hjle.common.exception.BusinessException;
import com.hjle.common.exception.ErrorCode;
import io.github.hjle.settlement.dto.OrderResponse;
import io.github.hjle.settlement.dto.SettlementEntity;
import io.github.hjle.settlement.dto.SettlementResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final OrderServiceClient orderServiceClient;
    private final SettlementRepository settlementRepository;

    @Transactional
    public void runSettlement() {
        List<OrderResponse> deliveredOrders = orderServiceClient.getOrdersByStatus("DELIVERED");

        for (OrderResponse order : deliveredOrders) {
            long totalAmount = order.getTotalPrice();
            long feeAmount = (long) (totalAmount * 0.1);
            long settlementAmount = totalAmount - feeAmount;

            SettlementEntity settlement = SettlementEntity.builder()
                    .userId(order.getUserId())
                    .orderId(order.getId())
                    .totalAmount(totalAmount)
                    .feeAmount(feeAmount)
                    .settlementAmount(settlementAmount)
                    .status(SettlementStatus.READY)
                    .build();

            settlementRepository.save(settlement);
        }
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public SettlementResponse getSettlementByOrderId(Long orderId) {
        SettlementEntity entity = settlementRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        return SettlementResponse.from(entity);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<SettlementResponse> getSettlementsByUserId(String userId) {
        return settlementRepository.findByUserId(userId)
                .stream()
                .map(SettlementResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public SettlementResponse completeSettlement(Long orderId) {
        SettlementEntity entity = settlementRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        if (SettlementStatus.COMPLETED == entity.getStatus()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        entity.completeSettlement();
        return SettlementResponse.from(entity);
    }
}
