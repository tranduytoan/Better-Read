package dbmsforeveread.foreveread.publisher;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
