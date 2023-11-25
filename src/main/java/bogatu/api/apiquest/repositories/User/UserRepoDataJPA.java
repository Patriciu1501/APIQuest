package bogatu.api.apiquest.repositories.User;

import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserInfoProjection;
import bogatu.api.apiquest.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepoDataJPA extends JpaRepository<User, Integer> {


    @Query(value = """
            UPDATE User u SET u.score = u.score + ?2 WHERE u.email = ?1""")
    @Modifying
    void increaseScore(String email, int toAdd);


    Optional<User> findByEmail(String email);
}
