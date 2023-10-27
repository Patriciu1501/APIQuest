package bogatu.api.apiquest.mappers;

import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserRegistrationResponse entityToDtoResponse(User user);
    User dtoRequestToEntity(UserRegistrationRequest userRegistrationRequest);
}
