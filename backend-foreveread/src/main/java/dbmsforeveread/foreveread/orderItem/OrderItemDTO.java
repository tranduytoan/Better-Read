package dbmsforeveread.foreveread.orderItem;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemDTO {
    private Long id;
    private Long bookId;
    private Integer quantity;
    private BigDecimal price;
}