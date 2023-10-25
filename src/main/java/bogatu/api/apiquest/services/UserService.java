package bogatu.api.apiquest.services;

import bogatu.api.apiquest.dtos.UserRegistrationRequest;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.exceptions.DuplicateException;
import bogatu.api.apiquest.repositories.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserDAO userDAO;

    @Transactional
    public void registerUser(UserRegistrationRequest userRegistrationRequest){
        if(userDAO.findUserByEmail(userRegistrationRequest.email()).isPresent()){
            throw new DuplicateException(userRegistrationRequest.email() + " already taken");
        }

        userDAO.registerUser(
                User.builder()
                        .username(userRegistrationRequest.username())
                        .password(userRegistrationRequest.password())
                        .email(userRegistrationRequest.email())
                        .userType(User.UserType.REGISTERED)
                        .build()
        );
    }
}
