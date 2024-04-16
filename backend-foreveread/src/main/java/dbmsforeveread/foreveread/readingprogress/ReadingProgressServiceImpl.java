package dbmsforeveread.foreveread.readingprogress;

import dbmsforeveread.foreveread.book.Book;
import dbmsforeveread.foreveread.book.BookRepository;
import dbmsforeveread.foreveread.exceptions.ResourceNotFoundException;
import dbmsforeveread.foreveread.user.User;
import dbmsforeveread.foreveread.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadingProgressServiceImpl implements ReadingProgressService {
    private final ReadingProgressRepository readingProgressRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public Page<ReadingProgressDTO> getReadingProgressByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return readingProgressRepository.findByUser(user, pageable)
                .map(this::mapToReadingProgressDTO);
    }

    @Override
    @Transactional
    public void updateReadingProgress(Long bookId, Long userId, ReadingStatus progress, int currentPage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        ReadingProgress readingProgress = readingProgressRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new ResourceNotFoundException("Reading progress not found for the given user and book"));
        readingProgress.setProgress(progress);
        readingProgressRepository.save(readingProgress);
    }

    @Override
    @Transactional
    public ReadingProgressDTO addBookToReadingProgress(Long bookId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        Optional<ReadingProgress> existingReadingProgress = readingProgressRepository.findByUserAndBook(user, book);
        if (existingReadingProgress.isPresent()) {
            throw new IllegalArgumentException("Book is already in list");
        }
        ReadingProgress readingProgress = new ReadingProgress();
        readingProgress.setUser(user);
        readingProgress.setBook(book);
        readingProgress.setProgress(ReadingStatus.NOT_STARTED);
        ReadingProgress savedProgress = readingProgressRepository.save(readingProgress);
        return mapToReadingProgressDTO(savedProgress);
    }

    @Override
    public List<ReadingProgressDTO> getInProgressBooksByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return readingProgressRepository.findInProgressBooksByUser(user)
                .stream()
                .map(this::mapToReadingProgressDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReadingProgressDTO> getCompletedBooksByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return readingProgressRepository.findCompletedBooksByUser(user)
                .stream()
                .map(this::mapToReadingProgressDTO)
                .collect(Collectors.toList());
    }

    private ReadingProgressDTO mapToReadingProgressDTO(ReadingProgress readingProgress) {
        return new ReadingProgressDTO(
                readingProgress.getUser().getId(),
                readingProgress.getBook().getId(),
                readingProgress.getBook().getTitle(),
                readingProgress.getBook().getImageUrl(),
                readingProgress.getProgress()
                );
    }

    @Override
    @Transactional
    public void removeBookFromReadingProgress(Long bookId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        ReadingProgress readingProgress = readingProgressRepository.findByUserAndBook(user, book)
                .orElseThrow(() -> new ResourceNotFoundException("Reading progress not found for the given user and book"));

        readingProgressRepository.delete(readingProgress);
    }
}
