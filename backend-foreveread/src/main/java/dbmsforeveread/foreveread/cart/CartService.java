package dbmsforeveread.foreveread.cart;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.book.BookService;
import dbmsforeveread.foreveread.cartItem.CartItem;
import dbmsforeveread.foreveread.cartItem.CartItemDTO;
import dbmsforeveread.foreveread.cartItem.CartItemRepository;
import dbmsforeveread.foreveread.exceptions.BookNotFoundException;
import dbmsforeveread.foreveread.exceptions.InsufficientInventoryException;
import dbmsforeveread.foreveread.inventory.Inventory;
import dbmsforeveread.foreveread.inventory.InventoryRepository;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final InventoryRepository inventoryRepository;
    private final BookService bookService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BookNotFoundException("Cart not found for user: " + userId));
    }

    @Transactional(readOnly = true)
    public CartDTO getCartDTOByUserId(Long userId) {
        Cart cart = getCartByUserId(userId);
        return mapCartToDTO(cart);
    }

    private CartDTO mapCartToDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
//                .user((cart.getUser()))
//                .createdAt(cart.getCreatedAt())
                .cartItems(mapCartItemsToDTO(cart.getCartItems()))
                .totalItems(calculateTotalItems(cart))
                .totalPrice(calculateCartTotal(cart))
                .build();
    }

    private List<CartItemDTO> mapCartItemsToDTO(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> CartItemDTO.builder()
                        .bookId(item.getBook().getId())
                        .bookImageUrl(item.getBook().getImageUrl())
                        .bookTitle(item.getBook().getTitle())
                        .price(item.getBook().getPrice())
                        .quantity(item.getQuantity())
                        .total(item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .toList();
    }

    private int calculateTotalItems(Cart cart) {
        return cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    private BigDecimal calculateCartTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Transactional
    public Cart createNewCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BookNotFoundException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart addToCart(Long bookId, Long userId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(userId));

        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found in inventory"));

        if (inventory.getQuantity() < quantity) {
            throw new InsufficientInventoryException("Insufficient inventory for the requested quantity");
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId);
        CartItem cartItem;

        if (optionalCartItem.isPresent()) {
            cartItem = optionalCartItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            validateInventoryAvailability(inventory, newQuantity);
            cartItem.setQuantity(newQuantity);
        } else {
            Book book = bookService.getBookById(bookId);
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cart.addCartItem(cartItem);
        }

        cartItemRepository.save(cartItem);
        updateInventoryQuantity(inventory, -quantity);

        return cart;
    }

    @Transactional
    public Cart updateQuantity(Long bookId, Long userId, int quantity) {
        Cart cart = getCartByUserId(userId);
        CartItem cartItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
                .orElseThrow(() -> new BookNotFoundException("Cart item not found"));

        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found in inventory"));

        int quantityDifference = quantity - cartItem.getQuantity();
        validateInventoryAvailability(inventory, quantityDifference);

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        updateInventoryQuantity(inventory, -quantityDifference);

        return cart;
    }

    @Transactional
    public Cart removeFromCart(Long bookId, Long userId) {
        Cart cart = getCartByUserId(userId);
        CartItem cartItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
                .orElseThrow(() -> new BookNotFoundException("Cart item not found"));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found in inventory"));

        updateInventoryQuantity(inventory, cartItem.getQuantity());

        return cart;
    }

    @Transactional
    public Cart clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        for (CartItem cartItem : cartItems) {
            Inventory inventory = inventoryRepository.findByBookId(cartItem.getBook().getId())
                    .orElseThrow(() -> new BookNotFoundException("Book not found in inventory"));

            updateInventoryQuantity(inventory, cartItem.getQuantity());
        }

        cartItemRepository.deleteByCartId(cart.getId());
        cart.getCartItems().clear();

        return cart;
    }

    private void validateInventoryAvailability(Inventory inventory, int requestedQuantity) {
        if (requestedQuantity > inventory.getQuantity()) {
            throw new InsufficientInventoryException("Insufficient inventory for the requested quantity");
        }
    }

    private void updateInventoryQuantity(Inventory inventory, int quantityChange) {
        inventory.setQuantity(inventory.getQuantity() + quantityChange);
        inventoryRepository.save(inventory);
    }
}