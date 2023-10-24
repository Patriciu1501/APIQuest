package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserDAO{

    List<User> getAllUsers();
    Optional<User> findUserById(Integer id);
    Optional<User> findUserByEmail(String email);
    boolean verifyUserEmailExists(String email);
    boolean verifyUserIdExists(int id);
    void registerUser(User user);
    void deleteUser(int id);
    void updateUser(User customer);
}

