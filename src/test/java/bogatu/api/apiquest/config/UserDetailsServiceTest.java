package bogatu.api.apiquest.config;

import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.exceptions.UserNotFoundException;
import bogatu.api.apiquest.repositories.User.UserDAO;
import bogatu.api.apiquest.repositories.User.UserDataJPA;
import bogatu.api.apiquest.tools.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserDetailsServiceTest {


    UserDetailsService underTest;
    UserDAO userDAO;


    @BeforeEach
    void beforeEach(){
        userDAO = Mockito.mock(UserDataJPA.class);
        underTest = u ->
                userDAO.findUserByEmail(u).orElseThrow(
                        () -> new UserNotFoundException("Not found")
                );
    }


    @Test
    @DisplayName("""
            Successfully fetching a user""")
    void test1(){
        String givenEmail = "randomEmail@mail.ro";
        User userToReturn = InstanceProvider.UserProvider.randomUser();

        given(userDAO.findUserByEmail(givenEmail)).willReturn(Optional.of(userToReturn));

        var expectedUserDetails = underTest.loadUserByUsername(givenEmail);

        verify(userDAO).findUserByEmail(givenEmail);

        assertThat(expectedUserDetails).isEqualTo(userToReturn);
    }


    @Test
    @DisplayName("""
            User not found when attempting to fetch by email""")
    void test2(){
        String givenEmail = "randomEmail@mail.ro";

        given(userDAO.findUserByEmail(givenEmail)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.loadUserByUsername(givenEmail))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Not found");

        verify(userDAO).findUserByEmail(givenEmail);
    }
}