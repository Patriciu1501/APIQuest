package bogatu.api.apiquest.mappers;

import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserRegistrationResponse entityToDtoResponse(User user);
    User dtoRequestToEntity(UserRegistrationRequest userRegistrationRequest);
    UserUpdateDTO entityToUpdateDto(User user);
    User updateDtoToEntity(UserUpdateDTO userUpdateDTO);
    UserInfo entityToUserInfo(User user);
    User userInfoToEntity(UserInfo userInfo);
}
