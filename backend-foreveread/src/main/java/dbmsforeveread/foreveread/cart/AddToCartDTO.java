package dbmsforeveread.foreveread.cart;

import lombok.Data;

@Data
public class AddToCartDTO {
    private Long userId;
    private int quantity;
    private Long bookId;
}
