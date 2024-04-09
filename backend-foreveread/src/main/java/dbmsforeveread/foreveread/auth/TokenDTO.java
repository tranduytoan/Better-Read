package dbmsforeveread.foreveread.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
