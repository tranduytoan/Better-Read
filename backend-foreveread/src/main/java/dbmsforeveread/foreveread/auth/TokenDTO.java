package dbmsforeveread.foreveread.auth;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
