package bogatu.api.apiquest.services.User;

import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest request);
    List<UserInfo> getAllUsers(int pageNumber);
    UserUpdateDTO updateUser(UserUpdateDTO userUpdateDTO, int id);
    UserInfo findUserById(int id);
    UserInfo getMyProfile(Authentication authentication);
    void increaseScore(Authentication authentication, int toAdd);
}
