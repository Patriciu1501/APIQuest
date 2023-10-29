package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepoDataJPA extends JpaRepository<User, Integer> {

    @Query("SELECT new bogatu.api.apiquest.dtos.User.UserInfo(u.id, u.username, u.email, u.createdAt, u.updatedAt) FROM User u" +
            " ORDER BY u.id")
    @Transactional(readOnly = true)
    Page<UserInfo> findAllUsers(Pageable pageable);


    @Transactional(readOnly = true)
    Optional<User> findUserByEmail(String email);
}
