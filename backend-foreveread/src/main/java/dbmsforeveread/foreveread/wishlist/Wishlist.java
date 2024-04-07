package dbmsforeveread.foreveread.wishlist;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Wishlist {
    @EmbeddedId
    private WishlistId id;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
}