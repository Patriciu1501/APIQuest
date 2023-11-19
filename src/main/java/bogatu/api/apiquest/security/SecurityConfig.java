package bogatu.api.apiquest.security;


import bogatu.api.apiquest.security.filters.JWTGeneratorFilter;
import bogatu.api.apiquest.security.filters.JWTValidatorFilter;
import bogatu.api.apiquest.exceptions.UserNotFoundException;
import bogatu.api.apiquest.repositories.User.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig{

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JWTValidatorFilter validator,
                                            JWTGeneratorFilter generator) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
                reqs -> reqs.requestMatchers("/api/apis/**").authenticated()
                        .requestMatchers("/api/profile/**").hasRole("USER")
                        .requestMatchers("/api/defaultApis/**").hasRole("USER")
                        .anyRequest().permitAll()
        );

        http.httpBasic(Customizer.withDefaults());

        http.addFilterBefore(validator, BasicAuthenticationFilter.class);
        http.addFilterAfter(generator, BasicAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    UserDetailsService userDetailsService(UserDAO userDAO){
        return u ->
            userDAO.findUserByEmail(u).orElseThrow(
                    () -> new UserNotFoundException("Not found")
            );
    }



    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
