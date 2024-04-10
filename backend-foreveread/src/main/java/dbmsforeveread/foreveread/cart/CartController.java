package dbmsforeveread.foreveread.cart;

import dbmsforeveread.foreveread.book.BookService;
import dbmsforeveread.foreveread.cartItem.CartItem;
import dbmsforeveread.foreveread.cartItem.CartItemDTO;
import dbmsforeveread.foreveread.exceptions.InsufficientInventoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestParam Long userId) {
        CartDTO cartDTO = cartService.getCartDTOByUserId(userId);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long bookId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        try {
            Cart cart = cartService.addToCart(bookId, userId, quantity);
//            CartDTO cartDTO = mapCartToDTO(cart);
            return ResponseEntity.ok(cart);
        } catch (InsufficientInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/items/{bookId}")
    public ResponseEntity<Cart> updateQuantity(
            @PathVariable Long bookId,
            @RequestParam Long userId,
            @RequestParam int quantity
    ) {
            Cart cart = cartService.updateQuantity(bookId, userId, quantity);
            return ResponseEntity.ok(cart);
//
    }
//
//    @DeleteMapping("/items/{bookId}")
//    public ResponseEntity<CartDTO> removeFromCart(
//            @PathVariable Long bookId,
//            @RequestParam Long userId
//    ) {
//        Cart cart = cartService.removeFromCart(bookId, userId);
//        CartDTO cartDTO = mapCartToDTO(cart);
//        return ResponseEntity.ok(cartDTO);
//    }
//
    @DeleteMapping
    public ResponseEntity<Cart> clearCart(@RequestParam Long userId) {
        Cart cart = cartService.clearCart(userId);
        return ResponseEntity.ok(cart);
    }
}