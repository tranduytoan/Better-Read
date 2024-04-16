package dbmsforeveread.foreveread.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;
    private String address;
    private String phone;

}