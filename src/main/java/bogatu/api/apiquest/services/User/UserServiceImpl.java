package bogatu.api.apiquest.services.User;

import bogatu.api.apiquest.controllers.DefaultAPIs;
import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.exceptions.DuplicateException;
import bogatu.api.apiquest.exceptions.RequestValidationException;
import bogatu.api.apiquest.exceptions.UserNotFoundException;
import bogatu.api.apiquest.mappers.APIMapper;
import bogatu.api.apiquest.mappers.UserMapper;
import bogatu.api.apiquest.repositories.API.APIDao;
import bogatu.api.apiquest.repositories.User.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;
    private final APIDao apiDao;
    private final UserMapper userMapper;
    private final APIMapper apiMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;


    @Transactional
    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest){
        userDAO.findUserByEmail(userRegistrationRequest.email())
                .ifPresent(c -> {throw new DuplicateException(userRegistrationRequest.email() + " already taken");});


        User entityToSave = userMapper.dtoRequestToEntity(userRegistrationRequest);
        entityToSave.setPassword(passwordEncoder.encode(entityToSave.getPassword()));
//        entityToSave.setRoleId(User.UserType.ROLE_USER.getId());
        var defaultAPIs = apiDao.getAllDefaults();

        defaultAPIs.forEach(a -> a.getUsers().add(entityToSave));
        entityToSave.setApiSet(Set.copyOf(defaultAPIs));

        defaultAPIs.forEach(apiDao::registerAPI);

        return userMapper.entityToDtoResponse(
                userDAO.registerUser(
                        entityToSave
                )
        );
    }


    @Override
    public List<UserInfo> getAllUsers(int pageNumber){
        int pageSize;
        if(pageNumber == -1){
            pageNumber = 0;
            pageSize = Integer.MAX_VALUE;
        }else{
            pageNumber = Math.max(0, pageNumber);
            pageSize = 4; // hardcoded, better make a constant somewhere
        }

        return userDAO.getAllUsers(pageNumber, pageSize)
                .stream()
                .map(userMapper::entityToUserInfo)
                .toList();
    }

    @Override
    public UserInfo findUserById(int id){
        return userMapper.entityToUserInfo(
                userDAO.findUserById(id).orElseThrow(() -> new UserNotFoundException(id + " not found"))
        );
    }

    @Transactional
    @Override
    public UserUpdateDTO updateUser(UserUpdateDTO userUpdateDTO, int id){
        User foundUser = userDAO.findUserById(id).orElseThrow(() -> new UserNotFoundException(id + " not found"));

        Stream.of(userUpdateDTO.username(), userUpdateDTO.email(), userUpdateDTO.password())
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(() -> new RequestValidationException("Empty body provided"));

        if(userUpdateDTO.email() != null
        && !userUpdateDTO.email().equals(foundUser.getEmail())
        && userDAO.findUserByEmail(userUpdateDTO.email()).isPresent())
            throw new DuplicateException(userUpdateDTO.email() + "already taken");

        if(userUpdateDTO.email() != null) foundUser.setEmail(userUpdateDTO.email());
        if(userUpdateDTO.username() != null) foundUser.setUsername(userUpdateDTO.username());
        if(userUpdateDTO.password() != null) foundUser.setPassword(userUpdateDTO.password());

        foundUser.setUpdatedAt(LocalDateTime.now());

        return userMapper.entityToUpdateDto(foundUser);
    }


    @Override
    public UserInfo getMyProfile(Authentication authentication){
        UserInfo user  = userMapper.entityToUserInfo(
                userDAO.findUserByEmail(authentication.getName()).orElseThrow()
        );


        DefaultAPIs.appendDefaultApis(user.getApiSet());
        return user;
    }


    @Override
    @Transactional
    public void increaseScore(Authentication authentication, int toAdd){
        userDAO.increaseScore(authentication.getName(), toAdd);
    }


    @Transactional
    public Optional<?> invokeMyAPI(String apiName, Authentication authentication){
        User user = userDAO.findUserByEmail(authentication.getName()).orElseThrow();

        var api = user.getApiSet().stream()
                .filter(a -> a.getName().equals(apiName))
                .findFirst()
                .orElseThrow(() -> new RequestValidationException("User does not have this api"));

        user.setScore(user.getScore() + api.getScore());

        var result = restTemplate.getForEntity(api.getEndpoint(), HashMap.class)
                .getBody();

        return Optional.ofNullable(result);
    }
}
