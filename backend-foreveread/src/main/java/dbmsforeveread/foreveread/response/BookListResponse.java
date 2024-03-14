package dbmsforeveread.foreveread.response;

import dbmsforeveread.foreveread.model.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookListResponse {
    private List<Book> books;
    private int totalPages;
}
