package bogatu.api.apiquest.repositories.RefreshToken;

import bogatu.api.apiquest.documents.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    @Modifying
    @Query("""
        {'_id' :  ?0
        }""")
    void invalidateRefreshToken(String id, boolean valid);
}
