package dbmsforeveread.foreveread.order;

import dbmsforeveread.foreveread.cart.Cart;
import dbmsforeveread.foreveread.cart.CartService;
import dbmsforeveread.foreveread.orderItem.OrderItem;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserProfile;
import dbmsforeveread.foreveread.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;

    @Transactional()
    public Order checkout(Long userId) {
        User currentUser = userService.getUserById(userId);
        Cart cart = cartService.getCartByUser(currentUser);

        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(OrderStatus.PROCESSING);

        UserProfile userProfile = currentUser.getUserProfile();
        if (userProfile != null) {
            String shippingAddress = getFullAddress(userProfile);
            order.setShippingAddress(shippingAddress);
            order.setBillingAddress(shippingAddress);
        } else {
            throw new RuntimeException("User's information not found");
        }

        order.setTotalAmount(cartService.calculateCartTotal(cart));

        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getBook().getPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        cartService.clearCart(userId);

        return order;
    }

    private String getFullAddress(UserProfile userProfile) {
        return userProfile.getId().toString();
//                getAddress() + ", " ;
//                +
//                userProfile.getCity() + ", " +
//                userProfile.getState() + " " +
//                userProfile.getZipCode() + ", " +
//                userProfile.getCountry();
    }
}
