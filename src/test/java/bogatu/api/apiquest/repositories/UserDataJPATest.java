package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.TestContainersTest;
import bogatu.api.apiquest.dtos.UserRegistrationRequest;
import bogatu.api.apiquest.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserDataJPATest extends TestContainersTest {

    UserDataJPA underTest;

    @Mock
    UserRepoDataJPA userRepoDataJPA;

    @BeforeEach
    void doSmh(){
        underTest = new UserDataJPA(userRepoDataJPA);
    }


    @Test
    @DisplayName("Successfully registering a user")
    void test1(){
        User givenUser = getUser();

        when(userRepoDataJPA.save(givenUser)).thenReturn(
                User.builder()
                        .id(1)
                        .email(givenUser.getEmail())
                        .username(givenUser.getUsername())
                        .password(givenUser.getPassword())
                        .userType(User.UserType.REGISTERED)
                        .createdAt(givenUser.getCreatedAt())
                        .build()
        );

        User registeredUser = underTest.registerUser(givenUser);

        verify(userRepoDataJPA).save(givenUser);

        assertThat(registeredUser).satisfies(c ->{
            assertThat(c.getEmail()).isEqualTo(givenUser.getEmail());
            assertThat(c.getUsername()).isEqualTo(givenUser.getUsername());
            assertThat(c.getPassword()).isEqualTo(givenUser.getPassword());
            assertThat(c.getUserType()).isEqualTo(givenUser.getUserType());
            assertThat(c.getCreatedAt()).isEqualTo(givenUser.getCreatedAt());
            assertThat(c.getId()).isEqualTo(1);
        });
    }

}