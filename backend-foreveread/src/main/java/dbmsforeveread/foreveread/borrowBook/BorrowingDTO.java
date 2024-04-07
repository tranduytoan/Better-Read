package dbmsforeveread.foreveread.borrowBook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingDTO {
    private String bookId;
    private String bookTitle;
    private String image;
    private int remainingDays;
    private LocalDateTime borrowedDate;
    private LocalDateTime returnDate;
}
