package dbmsforeveread.foreveread.borrowBook;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookInfo {
    @Id
    @Column(name= "book_id", unique = true)
    private String bookId;

    private String title;

    private String author;

    private String publisher;

    private String publicationYear;

    private int quantityInStock;

    private int totalQuantity;

    private boolean available;
}