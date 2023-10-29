package bogatu.api.apiquest.mappers;

import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.tools.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    UserMapper userMapper;


    @BeforeEach
    void beforeEach(){
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void test1(){
        User user = InstanceProvider.UserProvider.randomUser();

        var dtoRegResponse = userMapper.entityToDtoResponse(user);

        assertThat(dtoRegResponse.id()).isEqualTo(user.getId());
        assertThat(dtoRegResponse.username()).isEqualTo(user.getUsername());
        assertThat(dtoRegResponse.email()).isEqualTo(user.getEmail());
        assertThat(dtoRegResponse.createdAt()).isEqualTo(user.getCreatedAt());
    }
}