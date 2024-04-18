package dbmsforeveread.foreveread.SearchEngine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Document(indexName = "products")
public class Product {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type=FieldType.Text, name = "title")
    private String title;

    @Field(type=FieldType.Text, name = "category")
    private String category;

    @Field(type = FieldType.Text,name = "image_url")
    private String image_url;

    @Field(type = FieldType.Text, name = "publisher")
    private String publisher;

    @Field(type=FieldType.Text, name = "author")
    private String author;

    @Field(type = FieldType.Keyword,name = "language")
    private String language;

    @Field(type=FieldType.Float, name = "price")
    private Double price;

    @Field(type = FieldType.Text, name = "layout_book")
    private String layout_book;
}



