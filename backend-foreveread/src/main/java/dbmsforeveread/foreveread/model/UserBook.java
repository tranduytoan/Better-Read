package dbmsforeveread.foreveread.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "userBooks")
public class UserBook {
    @Id
    private String id;
    private String userId;
    private String bookId;
    private BookStatus status;

    @CreatedDate
    private String createdDate;

    @LastModifiedDate
    private String lastModifiedDate;

   public enum BookStatus {
       READING, READ, WANT_TO_READ
   }
}