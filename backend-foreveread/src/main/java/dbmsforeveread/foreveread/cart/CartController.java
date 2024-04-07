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
        Cart cart = cartService.getCartByUserId(userId);
        CartDTO cartDTO = mapCartToDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addToCart(
            @RequestParam Long bookId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        try {
            Cart cart = cartService.addToCart(bookId, userId, quantity);
            CartDTO cartDTO = mapCartToDTO(cart);
            return ResponseEntity.ok(cartDTO);
        } catch (InsufficientInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/items/{bookId}")
    public ResponseEntity<CartDTO> updateQuantity(
            @PathVariable Long bookId,
            @RequestParam Long userId,
            @RequestParam int quantity
    ) {
        try {
            Cart cart = cartService.updateQuantity(bookId, userId, quantity);
            CartDTO cartDTO = mapCartToDTO(cart);
            return ResponseEntity.ok(cartDTO);
        } catch (InsufficientInventoryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/items/{bookId}")
    public ResponseEntity<CartDTO> removeFromCart(
            @PathVariable Long bookId,
            @RequestParam Long userId
    ) {
        Cart cart = cartService.removeFromCart(bookId, userId);
        CartDTO cartDTO = mapCartToDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping
    public ResponseEntity<CartDTO> clearCart(@RequestParam Long userId) {
        Cart cart = cartService.clearCart(userId);
        CartDTO cartDTO = mapCartToDTO(cart);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@RequestParam Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        List<CartItemDTO> cartItemsDTOs = mapCartItemsToDTO(cart.getCartItems());
        return ResponseEntity.ok(cartItemsDTOs);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal(@RequestParam Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        BigDecimal total = calculateCartTotal(cart);
        return ResponseEntity.ok(total);
    }

    private BigDecimal calculateCartTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> {
                    BigDecimal bookPrice = item.getBook().getPrice();
                    return bookPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CartDTO mapCartToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setCreatedAt(cart.getCreatedAt());
        cartDTO.setCartItems(mapCartItemsToDTO(cart.getCartItems()));
        return cartDTO;
    }

    private List<CartItemDTO> mapCartItemsToDTO(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> {
                    CartItemDTO cartItemDTO = new CartItemDTO();
//                    cartItemDTO.setId(item.getId());
                    cartItemDTO.setBookId(item.getBook().getId());
                    cartItemDTO.setQuantity(item.getQuantity());
                    return cartItemDTO;
                })
                .toList();
    }
}