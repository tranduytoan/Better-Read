package dbmsforeveread.foreveread.order;

import dbmsforeveread.foreveread.orderItem.OrderItemDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String shippingAddress;
    private String billingAddress;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems;
}