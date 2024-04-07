package dbmsforeveread.foreveread.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class UserProfile {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String fullName;
    private String address;
    private String phone;
}
