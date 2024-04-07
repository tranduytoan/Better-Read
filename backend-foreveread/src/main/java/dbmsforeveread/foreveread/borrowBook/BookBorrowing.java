package dbmsforeveread.foreveread.borrowBook;

import dbmsforeveread.foreveread.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book_borrowing")
public class BookBorrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private BookInfo book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private LocalDateTime borrowedDate;

    private LocalDateTime dueDate;

    private LocalDateTime returnDate;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

//    private boolean isRenewed;

    private Double fine;

//    public void renewBook(LocalDateTime newDueDate) {
//        this.isRenewed = true;
//        this.dueDate = newDueDate;
//    }

}

