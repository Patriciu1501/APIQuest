package bogatu.api.apiquest.services.API;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.mappers.APIMapper;
import bogatu.api.apiquest.mappers.UserMapper;
import bogatu.api.apiquest.repositories.API.APIDao;
import bogatu.api.apiquest.repositories.API.APIRepoDataJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class APIServiceImpl implements APIService{

    private final APIDao apiDao;
    private final APIMapper apiMapper;

    @Transactional
    public APIDto registerAPI(APIDto request){
        return apiMapper.entityToDto(
                apiDao.registerAPI(apiMapper.dtoToEntity(request))
        );
    }


    public List<APIDto> getAllAPIs(){
        return apiDao.getAllAPIs();
    }
}
