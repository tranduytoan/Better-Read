package dbmsforeveread.foreveread.readingprogress;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, Long> {
    Page<ReadingProgress> findByUser(User user, Pageable pageable);
    Optional<ReadingProgress> findByUserAndBook(User user, Book book);

    @Query("SELECT rp FROM ReadingProgress rp WHERE rp.user = :user AND rp.progress = 'IN_PROGRESS'")
    List<ReadingProgress> findInProgressBooksByUser(User user);

    @Query("SELECT rp FROM ReadingProgress rp WHERE rp.user = :user AND rp.progress = 'COMPLETED'")
    List<ReadingProgress> findCompletedBooksByUser(User user);
}
