package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserUpdateDTO;
import bogatu.api.apiquest.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public sealed interface UserDAO permits UserDataJPA{

    List<UserInfo> getAllUsers(int pageNumber, int pageSize);
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    User registerUser(User user);
    void deleteUser(int id);
}

