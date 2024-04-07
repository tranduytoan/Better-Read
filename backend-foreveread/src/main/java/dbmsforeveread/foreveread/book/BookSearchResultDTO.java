package dbmsforeveread.foreveread.book;

import dbmsforeveread.foreveread.author.Author;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookSearchResultDTO {
    private Long id;
    private String title;
    private List<Author> author;
    private BigDecimal price;
    private String imageUrl;
}