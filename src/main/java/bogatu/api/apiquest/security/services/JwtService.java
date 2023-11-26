package bogatu.api.apiquest.security.services;

import bogatu.api.apiquest.documents.RefreshToken;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.repositories.RefreshToken.RefreshTokenRepository;
import bogatu.api.apiquest.repositories.User.UserDAO;
import bogatu.api.apiquest.security.details.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String jwtKey;
    private final long expiration;
    private final long refreshExpiration;
    private final UserDAO userDAO;
    private final RefreshTokenRepository refreshTokenRepository;
    public static final String BEARER_PREFIX = "Bearer ";


    public record Token(String accessToken, String refreshToken){}


    public JwtService(@Value("${apiquest.jwt.secretSigningKey}") String jwtKey,
                      @Value("${apiquest.jwt.expiration}") long accessExpiration,
                      @Value("${apiquest.jwt.refreshToken}") long refreshExpiration,
                      UserDAO userDAO, RefreshTokenRepository refreshTokenRepository){
        this.jwtKey = jwtKey;
        this.expiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.userDAO = userDAO;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public void validateToken(String jwtToken){
        if(jwtToken != null) {
            Claims claims;

            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

                claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwtToken.substring(BEARER_PREFIX.length()))
                        .getBody();

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token received" + e.getClass());
            }

            String subject = claims.getSubject();

            switch (subject) {
                case "JWT Access" -> handleAccessScenario(claims);
                case "JWT Refresh" -> handleRefreshScenario(claims, jwtToken);
            }
        }
    }


    private void handleAccessScenario(Claims claims){
        String username = claims.get("username", String.class);
        String authorities = claims.get("authorities", String.class);
        int score = claims.get("score", Integer.class);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

        auth.setDetails(score);

        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }


    private void handleRefreshScenario(final Claims claims, String tokenValue){
        String username = claims.get("username", String.class);

        userDAO.findUserByEmail(username).ifPresentOrElse(
                fu -> {
                    var u = new SecurityUser(fu);
                    var auth = new UsernamePasswordAuthenticationToken(username, null, u.getAuthorities());
                    refreshTokenRepository.findById(tokenValue.substring(BEARER_PREFIX.length()))
                                    .ifPresentOrElse(
                                            t -> {
                                                if(!t.isValid()) {
                                                    throw new RuntimeException("Used revoked refresh token");
                                                }

                                                refreshTokenRepository.save(
                                                        RefreshToken.builder()
                                                                .value(tokenValue.substring(BEARER_PREFIX.length()))
                                                                .valid(false)
                                                                .build()
                                                );
                                            },

                                            () ->{ throw new RuntimeException("No such token in the db");}
                                    );

                    Token details = generateToken(auth);

                    var unauthenticatedToken = new UsernamePasswordAuthenticationToken(username, null);
                    unauthenticatedToken.setDetails(details);
                    var context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(unauthenticatedToken);
                    SecurityContextHolder.setContext(context);
                },
                () -> {throw new RuntimeException("User is no longer valid");});
    }


    public Token generateToken(Authentication auth){
        SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        return new Token(generateAccessToken(auth, key),generateRefreshToken(auth.getName(), key));
    }


    private String generateAccessToken(Authentication auth, SecretKey secretKey){
        return Jwts.builder()
                .setIssuer("API QUEST")
                .setSubject("JWT Access")
                .claim("username", auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .claim("score", auth.getDetails())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }


    private String generateRefreshToken(String username, SecretKey secretKey){
        String token = Jwts.builder()
                .setIssuer("API QUEST")
                .setSubject("JWT Refresh")
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();

        refreshTokenRepository.insert(
                RefreshToken.builder()
                        .value(token)
                        .build()
        );

        return token;
    }
}
