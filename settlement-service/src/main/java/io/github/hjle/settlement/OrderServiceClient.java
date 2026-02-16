package io.github.hjle.settlement;

import io.github.hjle.settlement.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/order/status/{status}")
    List<OrderResponse> getOrdersByStatus(@PathVariable("status") String status);
}