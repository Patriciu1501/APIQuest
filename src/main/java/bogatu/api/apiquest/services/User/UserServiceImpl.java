package bogatu.api.apiquest.services.User;

import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.exceptions.DuplicateException;
import bogatu.api.apiquest.mappers.UserMapper;
import bogatu.api.apiquest.repositories.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Transactional
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest){
        userDAO.findUserByEmail(userRegistrationRequest.email())
                .ifPresent(c -> {throw new DuplicateException(userRegistrationRequest.email() + " already taken");});

        return userMapper.entityToDtoResponse(
                userDAO.registerUser(
                        userMapper.dtoRequestToEntity(userRegistrationRequest)
                )
        );
    }
}
