package bogatu.api.apiquest.services.User;

import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest request);
}
