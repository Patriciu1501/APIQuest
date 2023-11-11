package bogatu.api.apiquest.services.API;

import bogatu.api.apiquest.controllers.AuthController;
import bogatu.api.apiquest.controllers.DefaultAPIs;
import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.mappers.APIMapper;
import bogatu.api.apiquest.mappers.UserMapper;
import bogatu.api.apiquest.repositories.API.APIDao;
import bogatu.api.apiquest.repositories.API.APIRepoDataJpa;
import bogatu.api.apiquest.repositories.User.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class APIServiceImpl implements APIService{

    private final APIDao apiDao;
    private final APIMapper apiMapper;
    private final UserDAO userDAO;

    @Transactional
    public APIDto registerAPI(APIDto request){
        return apiMapper.entityToDto(
                apiDao.registerAPI(apiMapper.dtoToEntity(request))
        );
    }


    public List<APIDto> getMyAPIs(Authentication authentication){
        User user = userDAO.findUserByEmail(authentication.getName()).orElseThrow();
        return DefaultAPIs.appendDefaultApis(user, apiMapper);
    }

    public List<APIDto> getAllAPIs(){
        return apiDao.getAllAPIs();
    }
}
