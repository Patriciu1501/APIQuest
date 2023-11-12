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

    @Transactional(readOnly = true)
    @Query(
            nativeQuery = true,
            value = """
                    SELECT u.id, u.username, u.email, u.created_at, 
                    u.updated_at, u.password, u.role_id, u.score,
                    r.name
                    FROM Users u JOIN Roles r
                    ON u.role_id = r.id"""
    )
    Page<User> findAllUsers(Pageable pageable);


    @Transactional(readOnly = true)
    @Query(
            nativeQuery = true,
            value = """
                    SELECT u.id, u.username, u.email, u.created_at,
                    u.updated_at, u.password, u.role_id, u.score,
                    r.name
                    FROM Users u JOIN Roles r
                    ON u.role_id = r.id
                    WHERE u.email = :email"""
    )
    Optional<User> findUserByEmail(String email);


    @Query(value = """
            UPDATE User u SET u.score = u.score + ?2 WHERE u.email = ?1""")
    @Modifying
    void increaseScore(String email, int toAdd);

}
