package bogatu.api.apiquest.config;

import bogatu.api.apiquest.repositories.User.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
@RequiredArgsConstructor
public class APIQuestAuthProvider implements AuthenticationProvider {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();

        var existentCustomer = userDAO.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("No user with such email"));

        if(!passwordEncoder.matches(authentication.getCredentials().toString(), existentCustomer.getPassword()))
            throw new BadCredentialsException("Wrong password");


        return new UsernamePasswordAuthenticationToken(
                email, authentication.getCredentials().toString(), List.of()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
