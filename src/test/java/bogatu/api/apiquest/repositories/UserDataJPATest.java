package bogatu.api.apiquest.repositories;

import bogatu.api.apiquest.TestContainersTest;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.repositories.User.UserDataJPA;
import bogatu.api.apiquest.repositories.User.UserRepoDataJPA;
import bogatu.api.apiquest.tools.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserDataJPATest extends TestContainersTest {

    UserDataJPA underTest;

    @Mock
    UserRepoDataJPA userRepoDataJPA;

    @BeforeEach
    void doSmh() {
        underTest = new UserDataJPA(userRepoDataJPA);
    }


    @Test
    @DisplayName("Successfully registering a user")
    void test1() {
        User givenUser = InstanceProvider.UserProvider.randomUser();

        User toReturn = User.builder()
                .id(1)
                .username(givenUser.getUsername())
                .password(givenUser.getPassword())
                .email(givenUser.getEmail())
                .userType(givenUser.getUserType())
                .createdAt(givenUser.getCreatedAt())
                .build();


        when(userRepoDataJPA.save(givenUser)).thenReturn(toReturn);

        User registeredUser = underTest.registerUser(givenUser);

        verify(userRepoDataJPA).save(givenUser);

        assertThat(registeredUser).satisfies(c -> {
            assertThat(c.getEmail()).isEqualTo(givenUser.getEmail());
            assertThat(c.getUsername()).isEqualTo(givenUser.getUsername());
            assertThat(c.getPassword()).isEqualTo(givenUser.getPassword());
            assertThat(c.getUserType()).isEqualTo(givenUser.getUserType());
            assertThat(c.getCreatedAt()).isEqualTo(givenUser.getCreatedAt());
            assertThat(c.getId()).isEqualTo(toReturn.getId());
        });
    }

}