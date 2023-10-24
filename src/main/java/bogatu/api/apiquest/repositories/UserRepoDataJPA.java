package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepoDataJPA extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u")
    @Transactional(readOnly = true)
    List<User> findAll();

    @Transactional(readOnly = true)
    Optional<User> findUserByEmail(String email);
}
