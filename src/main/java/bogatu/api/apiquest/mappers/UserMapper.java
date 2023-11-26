package bogatu.api.apiquest.mappers;

import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.Role;
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
    default String map(Role role){
        return role.getName().substring(5);
    }

    default Role map(String role){
        int id = role.equals("ADMIN") ? 1 : (role.equals("USER") ? 2 : 3);
        return new Role(id, "ROLE_".concat(role), null);
    }
}
