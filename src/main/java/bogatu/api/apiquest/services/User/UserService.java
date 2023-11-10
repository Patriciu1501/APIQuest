package bogatu.api.apiquest.services.User;

import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.User;

import java.util.List;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest request);
    List<UserInfo> getAllUsers(int pageNumber);

    UserUpdateDTO updateUser(UserUpdateDTO userUpdateDTO, int id);

    UserInfo findUserById(int id);
}
