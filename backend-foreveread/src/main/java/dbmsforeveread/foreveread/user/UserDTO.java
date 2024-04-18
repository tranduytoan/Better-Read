package dbmsforeveread.foreveread.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
}
