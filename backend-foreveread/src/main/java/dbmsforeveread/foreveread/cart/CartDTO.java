package dbmsforeveread.foreveread.cart;

import dbmsforeveread.foreveread.cartItem.CartItem;
import dbmsforeveread.foreveread.cartItem.CartItemDTO;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private User user;
    private LocalDateTime createdAt;
    private List<CartItemDTO> cartItems;
    private int totalItems;
    private BigDecimal totalPrice;
}