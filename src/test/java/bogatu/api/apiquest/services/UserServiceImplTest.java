package bogatu.api.apiquest.services;

import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.exceptions.DuplicateException;
import bogatu.api.apiquest.mappers.APIMapper;
import bogatu.api.apiquest.mappers.UserMapper;
import bogatu.api.apiquest.repositories.API.APIDao;
import bogatu.api.apiquest.repositories.API.APIDataJpa;
import bogatu.api.apiquest.repositories.User.UserDAO;
import bogatu.api.apiquest.repositories.User.UserDataJPA;
import bogatu.api.apiquest.services.User.UserServiceImpl;
import bogatu.api.apiquest.tools.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserDAO userDAO;
    APIDao apiDao;
    @Mock
    UserMapper userMapper;
    @Mock
    APIMapper apiMapper;
    PasswordEncoder passwordEncoder;
    UserServiceImpl underTest;

    @BeforeEach
    void beforeEach(){
        userDAO = Mockito.mock(UserDataJPA.class);
        apiDao = Mockito.mock(APIDataJpa.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        underTest = new UserServiceImpl(userDAO, apiDao, userMapper, apiMapper, passwordEncoder, null);
    }


    @Test
    @DisplayName("""
            When trying to register throws exception
            because of duplicated email constraint""")
    void test1(){
        var givenRequest = InstanceProvider.UserProvider.randomRequest();

        when(userDAO.findUserByEmail(givenRequest.email())).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> underTest.registerUser(givenRequest))
                .isInstanceOf(DuplicateException.class)
                .hasMessage(givenRequest.email() + " already taken");

        verify(userDAO, never()).registerUser(any());
        verify(userMapper, never()).entityToDtoResponse(any());
    }


    @Test
    @DisplayName("""
            Successfully registering an user""")
    void test2(){
        var givenRequest = InstanceProvider.UserProvider.randomRequest();

        given(userDAO.findUserByEmail(givenRequest.email())).willReturn(Optional.empty());
        given(userMapper.dtoRequestToEntity(any())).willReturn(any());
        given(userDAO.registerUser(InstanceProvider.UserProvider.randomUser()))
                .willReturn(InstanceProvider.UserProvider.randomUser());

        var responseToReturn = InstanceProvider.UserProvider.randomResponse();
        given(userMapper.entityToDtoResponse(any())).willReturn(responseToReturn);

        assertThatNoException().isThrownBy(() -> {
            var receivedResponse = underTest.registerUser(givenRequest);
            assertThat(receivedResponse).isEqualTo(responseToReturn);
        });
    }



//    void test2(){
//        var givenRequest = InstanceProvider.UserProvider.randomRequest();
//
//        when(userDAO.findUserByEmail(givenRequest.email())).thenReturn(Optional.empty());
//        User convertedEntity = InstanceProvider.UserProvider.randomUser();
//        when(userMapper.dtoRequestToEntity(givenRequest)).thenReturn(convertedEntity);
//
//        User returnedEntity = InstanceProvider.UserProvider.randomUser();
//        returnedEntity.setId(1);
//        when(userDAO.registerUser(convertedEntity)).thenReturn(returnedEntity);
//
//        var responseToReturn = InstanceProvider.UserProvider.randomResponse();
//        when(userMapper.entityToDtoResponse(returnedEntity)).thenReturn(responseToReturn);
//
//        assertThatNoException().isThrownBy(() -> {
//            var receivedResponse = underTest.registerUser(givenRequest);
//
//        });
//
//    }

}