package dbmsforeveread.foreveread.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "authors")
public class Author {
    private String name;
    private String key;
    private List<String> source_records;
    private int latest_revision;
    private int revision;

    @CreatedDate
    private String created_at;

    @CreatedDate
    private String last_modified;
}
