package bogatu.api.apiquest.tools;

import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.entities.User;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public final class InstanceProvider {

    private static final Faker faker = new Faker();


    public final static class UserProvider{

        public static Stream<User> users(){
            return Stream.generate(UserProvider::randomUser);
        }

        public static Stream<UserRegistrationRequest> requests(){
            return Stream.generate(UserProvider::randomRequest);
        }


        public static Stream<UserInfo> userInfos(){
            return Stream.generate(UserProvider::randomUserInfo);
        }

        public static User randomUser(){
            return User.builder()
                    .apiQuestUsername(faker.name().username())
                    .password(faker.internet().password(true))
                    .email(faker.internet().emailAddress())
                    .userType(User.UserType.ROLE_USER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }


        public static UserInfo randomUserInfo(){
            return UserInfo
                    .builder()
                    .apiQuestUsername(faker.name().username())
                    .email(faker.internet().emailAddress())
                    .createdAt(LocalDateTime.now())
                    .build();
        }


        public static UserRegistrationRequest randomRequest(){
            return UserRegistrationRequest.builder()
                    .username(faker.name().username())
                    .password(faker.internet().password())
                    .email(faker.internet().emailAddress())
                    .build();
        }


        public static UserRegistrationResponse randomResponse(){
            return UserRegistrationResponse
                    .builder()
                    .id(new Random().nextInt())
                    .username(faker.name().username())
                    .email(faker.internet().emailAddress())
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }


    private InstanceProvider(){}
}
