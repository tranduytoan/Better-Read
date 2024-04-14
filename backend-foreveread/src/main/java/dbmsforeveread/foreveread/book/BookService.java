package dbmsforeveread.foreveread.book;

import dbmsforeveread.foreveread.author.Author;
import dbmsforeveread.foreveread.author.AuthorDTO;
import dbmsforeveread.foreveread.category.Category;
import dbmsforeveread.foreveread.category.CategoryDTO;
import dbmsforeveread.foreveread.inventory.Inventory;
import dbmsforeveread.foreveread.inventory.InventoryDTO;
import dbmsforeveread.foreveread.inventory.InventoryRepository;
import dbmsforeveread.foreveread.publisher.Publisher;
import dbmsforeveread.foreveread.publisher.PublisherDTO;
import dbmsforeveread.foreveread.publisher.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final InventoryRepository inventoryRepository;
    private final PublisherRepository publisherRepository;

    @Transactional(readOnly = true)
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    }


    public BigDecimal getBookPriceById(Long bookId) {
        Book book = getBookById(bookId);
        return book.getPrice();
    }

    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());
        return bookRepository.save(existingBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

//    public Page<BookSearchResultDTO> searchBooks(String query, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Book> books = bookRepository.findByTitleContainingIgnoreCase(query, pageable);
//        return books.map(this::convertToSearchResultDTO);
//    }
    public Page<BookSearchResultDTO> searchBooks(BookSearchRequest request) {
        String query = request.getQuery();
        int page = request.getPage();
        int size = request.getSize();
        String priceRange = request.getPrice();
//        List<Category> categories = request.getCategory();

        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        if (priceRange != null && !priceRange.isEmpty()) {
            String[] prices = priceRange.split("-");
            minPrice = new BigDecimal(prices[0]);
            maxPrice = prices.length > 1 ? new BigDecimal(prices[1]) : null;
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.searchBooks(query, minPrice, maxPrice, pageable);
        return bookPage.map(this::convertToSearchResultDTO);
    }

    private BookSearchResultDTO convertToSearchResultDTO(Book book) {
        BookSearchResultDTO dto = new BookSearchResultDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setImageUrl(book.getImageUrl());
        return dto;
    }

    public BookDTO getBookDetails(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        return convertToDTO(book);
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublisher(convertToPublisherDTO(book.getPublisher()));
        bookDTO.setPublicationDate(book.getPublicationDate());
        bookDTO.setLanguage(book.getLanguage());
        bookDTO.setPages(book.getPages());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setImageUrl(book.getImageUrl());
//        bookDTO.setAuthors(convertToAuthorDTOs(book.getAuthors()));
//        bookDTO.setCategoryIds(convertToCategoryDTOs(book.getCategories()));
        bookDTO.setInventory(convertToInventoryDTO(book.getInventory()));
        return bookDTO;
    }
    private PublisherDTO convertToPublisherDTO(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(publisher.getId());
        publisherDTO.setName(publisher.getName());
        return publisherDTO;
    }

    private List<AuthorDTO> convertToAuthorDTOs(List<Author> authors) {
        return authors.stream()
                .map(this::convertToAuthorDTO)
                .collect(Collectors.toList());
    }

    private AuthorDTO convertToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    private List<CategoryDTO> convertToCategoryDTOs(List<Category> categories) {
        return categories.stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private InventoryDTO convertToInventoryDTO(Inventory inventory) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setQuantity(inventory.getQuantity());
        return inventoryDTO;
    }

    public List<Category> getBookCategories(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return book.getCategories();
        }
        return Collections.emptyList();
    }
}
