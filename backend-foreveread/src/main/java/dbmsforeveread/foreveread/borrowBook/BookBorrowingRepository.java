package dbmsforeveread.foreveread.borrowBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Long> {
    List<BookBorrowing> findByUserId(Long userId);
}
