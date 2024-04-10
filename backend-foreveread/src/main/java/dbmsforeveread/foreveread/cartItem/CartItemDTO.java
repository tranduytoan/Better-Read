package dbmsforeveread.foreveread.cartItem;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class CartItemDTO {
    private Long bookId;
    private String bookImageUrl;
    private String bookTitle;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;
}
