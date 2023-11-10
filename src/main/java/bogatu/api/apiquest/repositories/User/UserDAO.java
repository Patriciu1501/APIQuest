package bogatu.api.apiquest.repositories.User;

import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserInfoProjection;
import bogatu.api.apiquest.entities.User;

import java.util.List;
import java.util.Optional;

public sealed interface UserDAO permits UserDataJPA{

    List<User> getAllUsers(int pageNumber, int pageSize);
    Optional<User> findUserById(int id);
    Optional<User> findUserByEmail(String email);
    User registerUser(User user);
    void deleteUser(int id);
}

