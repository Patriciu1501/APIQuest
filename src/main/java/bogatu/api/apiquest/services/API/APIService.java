package bogatu.api.apiquest.services.API;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.dtos.User.UserUpdateDTO;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface APIService {

    APIDto registerAPI(APIDto request);
    List<APIDto> getAllAPIs();
    List<APIDto> getAllDefaults();

    List<APIDto> getMyAPIs(Authentication authentication);
}
