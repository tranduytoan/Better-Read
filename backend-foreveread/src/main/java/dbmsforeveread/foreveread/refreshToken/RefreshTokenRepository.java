package dbmsforeveread.foreveread.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByOwner_Id(Long id);
    default void deleteByOwner_Id(String userIdFromRefreshToken) {
        deleteByOwner_Id(Long.parseLong(userIdFromRefreshToken));
    }
}
