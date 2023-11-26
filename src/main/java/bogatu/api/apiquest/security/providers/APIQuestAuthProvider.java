package bogatu.api.apiquest.security.providers;

import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.repositories.User.UserDAO;
import bogatu.api.apiquest.security.details.SecurityUser;
import bogatu.api.apiquest.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class APIQuestAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();

        var userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                email, null, userDetails.getAuthorities()
        );

        auth.setDetails(((SecurityUser) userDetails).getScore());

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
