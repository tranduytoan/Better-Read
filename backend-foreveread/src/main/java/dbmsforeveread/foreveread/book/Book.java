package dbmsforeveread.foreveread.book;

import dbmsforeveread.foreveread.author.Author;
import dbmsforeveread.foreveread.category.Category;
import dbmsforeveread.foreveread.inventory.Inventory;
import dbmsforeveread.foreveread.publisher.Publisher;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    private LocalDate publicationDate;
    private String language;
    private Integer pages;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @ManyToMany
    @JoinTable(name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne(mappedBy = "book")
    private Inventory inventory;
}