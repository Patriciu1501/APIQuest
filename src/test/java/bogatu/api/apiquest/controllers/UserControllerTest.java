package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.security.SecurityConfig;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.services.User.UserService;
import bogatu.api.apiquest.services.User.UserValidatorService;
import bogatu.api.apiquest.tools.InstanceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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