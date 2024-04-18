package dbmsforeveread.foreveread.book;

import dbmsforeveread.foreveread.category.Category;
import lombok.Data;

import java.util.List;

@Data
public class BookSearchRequest {
    private String query;
//    private int page;
//    private int size;
//    private String price;
//    private List<Category> category;
//    private String review;
}