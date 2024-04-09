package dbmsforeveread.foreveread.auth;

import dbmsforeveread.foreveread.user.User;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtToken {
    static final String issuer = "foreveread";

    @Value("#{${accessTokenExpirationMinutes} * 60 * 1000}")
    private int accessTokenExpirationMs;
    private long refreshTokenExpirationMs;

    private Algorithm accessTokenAlgorithm;
    private Algorithm refreshTokenAlgorithm;
    private JWTVerifier accessTokenVerifier;
    private JWTVerifier refreshTokenVerifier;

    public JwtToken(@Value("${accessTokenSecret}") String accessTokenSecret, @Value("${refreshTokenSecret}") String refreshTokenSecret, @Value("${refreshTokenExpirationDays}") int refreshTokenExpirationDays) {
        this.refreshTokenExpirationMs = (long) refreshTokenExpirationDays * 24 * 60 * 60 * 1000;
        this.accessTokenAlgorithm = Algorithm.HMAC512(accessTokenSecret);
        this.refreshTokenAlgorithm = Algorithm.HMAC512(refreshTokenSecret);
        this.accessTokenVerifier = JWT.require(accessTokenAlgorithm).withIssuer(issuer).build();
        this.refreshTokenVerifier = JWT.require(refreshTokenAlgorithm).withIssuer(issuer).build();
    }

    public String generateAccessToken(User User) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(String.valueOf(User.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .sign(accessTokenAlgorithm);
    }

    public String generateRefreshToken(User User, String tokenId) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(String.valueOf(User.getId()))
                .withClaim("tokenId", tokenId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .withJWTId(tokenId)
                .sign(refreshTokenAlgorithm);
    }

    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<DecodedJWT> decodeRefreshToken(String token) {
        try {
            return Optional.of(refreshTokenVerifier.verify(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateAccessToken(String token) {
        return decodeAccessToken(token).isPresent();
    }

    public boolean validateRefreshToken(String token) {
        return decodeRefreshToken(token).isPresent();
    }

    public String getUserIdFromAccessToken(String token) {
        return decodeAccessToken(token)
                .map(DecodedJWT::getSubject)
                .orElse(null);
    }

    public String getUserIdFromRefreshToken(String token) {
        return decodeRefreshToken(token)
                .map(DecodedJWT::getSubject)
                .orElse(null);
    }

    public String getTokenIdFromRefreshToken(String token) {
        return decodeRefreshToken(token)
                .map(jwt -> jwt.getClaim("tokenId").asString())
                .orElse(null);
    }
}
