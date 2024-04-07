package dbmsforeveread.foreveread.cartItem;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartItemDTO {
    private Long bookId;
    private String bookTitle;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;
}
