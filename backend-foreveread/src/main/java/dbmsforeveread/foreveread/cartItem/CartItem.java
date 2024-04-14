package dbmsforeveread.foreveread.cartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.cart.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private int quantity;

}
