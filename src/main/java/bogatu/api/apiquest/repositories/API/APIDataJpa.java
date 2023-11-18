package bogatu.api.apiquest.repositories.API;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.API;
import bogatu.api.apiquest.repositories.API.APIDao;
import bogatu.api.apiquest.repositories.API.APIRepoDataJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class APIDataJpa implements APIDao {

    private final APIRepoDataJpa apiRepoDataJpa;

    @CacheEvict(value = {"apis", "defaultApis"}, allEntries = true)
    public API registerAPI(API api){
        return apiRepoDataJpa.save(api);
    }

    @Cacheable("apis")
    public List<APIDto> getAllAPIs(){
        return apiRepoDataJpa.getAllAPIs();
    }


    @Cacheable("defaultApis")
    public List<API> getAllDefaults(){
        return apiRepoDataJpa.getAllDefaults();
    }
}
