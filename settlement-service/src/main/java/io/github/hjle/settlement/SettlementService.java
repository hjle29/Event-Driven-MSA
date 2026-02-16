package io.github.hjle.settlement;

import io.github.hjle.settlement.dto.OrderResponse;
import io.github.hjle.settlement.dto.SettlementEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final OrderServiceClient orderServiceClient;
    private final SettlementRepository settlementRepository;

    @Transactional
    public void runSettlement() {
        // 1. 배송 완료된 주문들만 가져오기
        List<OrderResponse> deliveredOrders = orderServiceClient.getOrdersByStatus("DELIVERED");

        // 2. 주문 건별로 정산 데이터 생성 (수수료 10% 계산)
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
                    .status("READY")
                    .build();

            settlementRepository.save(settlement);
        }
    }
}