package bogatu.api.apiquest.config.filters;

import bogatu.api.apiquest.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class JWTGeneratorFilter extends OncePerRequestFilter {

    private final String jwtKey;

    private final long expiration;
    private final long refreshExpiration;

    public JWTGeneratorFilter(@Value("${apiquest.jwt.secretSigningKey}") String jwtKey,
                              @Value("${apiquest.jwt.expiration}") long accessExpiration,
                              @Value("${apiquest.jwt.refreshToken}") long refreshExpiration){
        this.jwtKey = jwtKey;
        this.expiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !(request.getServletPath().equals("/api/auth/login")
                || request.getServletPath().equals("/api/auth/refreshToken"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setIssuer("API-QUEST")
                    .setSubject("JWT Token")
                    .claim("username", auth.getName())
                    .claim("authorities", auth.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(",")))
                    .claim("score", auth.getDetails())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(key)
                    .compact();

            response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
        }else{
            String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);


        }

        filterChain.doFilter(request, response);
    }
}
