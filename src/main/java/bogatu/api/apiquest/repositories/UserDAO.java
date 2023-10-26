package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public sealed interface UserDAO permits UserDataJPA{

    List<User> getAllUsers();
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    User registerUser(User user);
    void deleteUser(int id);
    void updateUser(User customer);
}

