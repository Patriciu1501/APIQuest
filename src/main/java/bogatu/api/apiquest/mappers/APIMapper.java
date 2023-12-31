package bogatu.api.apiquest.mappers;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.entities.API;
import bogatu.api.apiquest.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface APIMapper {

    API dtoToEntity(APIDto apiRegistrationRequest);
    default APIDto entityToDto(API entity){
        return new APIDto(
                entity.getName(), entity.getEndpoint(), entity.getScore(), entity.isDefault(),
                entity.getCreatedAt(), entity.getUpdatedAt()
                );
    }
}
