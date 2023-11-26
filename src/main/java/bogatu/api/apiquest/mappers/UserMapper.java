package bogatu.api.apiquest.mappers;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.Role;
import bogatu.api.apiquest.entities.User;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserRegistrationResponse entityToDtoResponse(User user);
    User dtoRequestToEntity(UserRegistrationRequest userRegistrationRequest);
    UserUpdateDTO entityToUpdateDto(User user);
    User updateDtoToEntity(UserUpdateDTO userUpdateDTO);
//    UserInfo entityToUserInfo(User user);

    default UserInfo entityToUserInfo(User entity){
        return UserInfo.builder()
                .email(entity.getEmail())
                .username(entity.getUsername())
                .id(entity.getId())
                .score(entity.getScore())
                .role(entity.getRole().getName().substring(5))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .apiSet(
                        entity.getApiSet()
                                .stream()
                                .map(a -> new APIDto(
                                        a.getName(),
                                        a.getEndpoint(),
                                        a.getScore(),
                                        a.isDefault(),
                                        a.getCreatedAt(),
                                        a.getUpdatedAt()
                                ))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
