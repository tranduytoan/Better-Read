package dbmsforeveread.foreveread.orderItem;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.order.Order;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer quantity;
    private BigDecimal price;
}