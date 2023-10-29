package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.config.SecurityConfig;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.services.User.UserService;
import bogatu.api.apiquest.services.User.UserServiceImpl;
import bogatu.api.apiquest.services.User.UserValidatorService;
import bogatu.api.apiquest.tools.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(controllers = {UserController.class})
@Import(SecurityConfig.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserValidatorService userValidatorService;

    @Autowired
    ObjectMapper objectMapper;


//    @TestConfiguration
//    static class SecurityConfig{
//        @Bean
//        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http.csrf(AbstractHttpConfigurer::disable);
//
//            http.authorizeHttpRequests(
//                    reqs -> reqs.anyRequest().permitAll()
//            );
//
//            return http.build();
//        }
//
//    }


    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    void test1(int objects) throws Exception {

        given(userService.getAllUsers(0))
                .willReturn(
                        InstanceProvider.UserProvider.userInfos().limit(objects).toList()
                );

        mockMvc.perform(get("/api/users?pageNumber=0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(objects)));
    }


    @Test
    void test2() throws Exception {

        var req = InstanceProvider.UserProvider.randomRequest();
        var res = InstanceProvider.UserProvider.randomResponse();

        given(userService.registerUser(any(UserRegistrationRequest.class))).willReturn(res);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(res)));
    }
}