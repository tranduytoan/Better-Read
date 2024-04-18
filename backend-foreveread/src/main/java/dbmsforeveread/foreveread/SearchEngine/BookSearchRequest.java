package dbmsforeveread.foreveread.SearchEngine;

import lombok.Data;

import java.util.List;
@Data
public class BookSearchRequest {
    private String title;
    private String author;
    private Double minPrice;
    private Double maxPrice;
    private List<String> categories;
}
