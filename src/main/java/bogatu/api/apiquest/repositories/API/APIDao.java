package bogatu.api.apiquest.repositories.API;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.API;

import java.util.List;

public interface APIDao {

    API registerAPI(API api);

    List<APIDto> getAllAPIs();
}
