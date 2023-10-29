package bogatu.api.apiquest.services.User;

import bogatu.api.apiquest.controllers.UserController;
import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.dtos.User.UserUpdateDTO;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.exceptions.DuplicateException;
import bogatu.api.apiquest.exceptions.RequestValidationException;
import bogatu.api.apiquest.exceptions.UserNotFoundException;
import bogatu.api.apiquest.mappers.UserMapper;
import bogatu.api.apiquest.repositories.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest){
        userDAO.findUserByEmail(userRegistrationRequest.email())
                .ifPresent(c -> {throw new DuplicateException(userRegistrationRequest.email() + " already taken");});

        return userMapper.entityToDtoResponse(
                userDAO.registerUser(
                        userMapper.dtoRequestToEntity(userRegistrationRequest)
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

        return userDAO.getAllUsers(pageNumber, pageSize);
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
}
