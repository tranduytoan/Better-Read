package dbmsforeveread.foreveread.borrowBook;

import dbmsforeveread.foreveread.book.BookRepository;
import dbmsforeveread.foreveread.exceptions.BookNotFoundException;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BookBorrowingRepository bookBorrowingRepository;
    private final BookInfoRepository bookInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public void borrowBook(String bookId, Long userId) {
        BookInfo bookInfo = bookInfoRepository.findByBookId(bookId);
        if (bookInfo == null) {
            throw new BookNotFoundException("not found with id: ");
        }

        if (bookInfo.getQuantityInStock() <= 0) {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BookNotFoundException("not found user"));

        BookBorrowing borrowing = new BookBorrowing();
        borrowing.setBook(bookInfo);
        borrowing.setUser(user);
        borrowing.setBorrowedDate(LocalDateTime.now());
        borrowing.setDueDate(LocalDateTime.now().plusDays(14));
        borrowing.setStatus(BorrowingStatus.BORROWED);

        bookInfo.setQuantityInStock(bookInfo.getQuantityInStock() - 1);
        bookInfo.setAvailable(bookInfo.getQuantityInStock() > 0);

        bookInfoRepository.save(bookInfo);
        bookBorrowingRepository.save(borrowing);

    }

    public List<BorrowingDTO> getUserBorrowings(Long userId) {
        List<BookBorrowing> borrowings = bookBorrowingRepository.findByUserId(userId);
        return borrowings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BorrowingDTO convertToDTO(BookBorrowing borrowing) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setBookId(borrowing.getBook().getBookId());
        dto.setBookTitle(borrowing.getBook().getTitle());
        dto.setImage("https://covers.openlibrary.org/b/id/" + borrowing.getBook().getBookId());
        dto.setRemainingDays(calulateRemainingDays(borrowing.getDueDate()));
        dto.setBorrowedDate(borrowing.getBorrowedDate());
        dto.setReturnDate(borrowing.getReturnDate());
        return dto;
    }

    private int calulateRemainingDays(LocalDateTime dueDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return (int) ChronoUnit.DAYS.between(currentDate, dueDate);
    }
}
