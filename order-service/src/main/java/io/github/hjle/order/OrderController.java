package io.github.hjle.order;

import com.hjle.common.dto.response.ApiResponse;
import io.github.hjle.order.dto.MemberResponse;
import io.github.hjle.order.dto.OrderEntity;
import io.github.hjle.order.dto.request.OrderRequest;
import io.github.hjle.order.dto.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final MemberServiceClient memberServiceClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderEntity savedOrder = orderService.createOrder(request);
        return ApiResponse.success(OrderResponse.from(savedOrder));
    }

    @GetMapping("/{userId}/with-member")
    public Map<String, Object> getOrderWithMember(@PathVariable String userId) {
        MemberResponse memberInfo = memberServiceClient.getMemberByUserId(userId);

        List<OrderEntity> orders = orderRepository.findByUserId(userId);
        if (CollectionUtils.isEmpty(orders)) {
            throw new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + userId);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orders", orders);
        result.put("member", memberInfo);

        return result;
    }
}
