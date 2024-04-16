package dbmsforeveread.foreveread.readingprogress;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ReadingProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private ReadingStatus progress;
}
