package bogatu.api.apiquest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.usertype.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(
        name = "Users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_email",
                        columnNames = "email"
                )
        }
)

@NoArgsConstructor
@Getter
@Setter
@SecondaryTable(name = "Roles")
public class User extends GenericEntity implements UserDetails {

    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @Column(columnDefinition = "BIGSERIAL")
    @Id
    private int id;
    //renamed to avoid ambiguity the getUsername() by lombok and getUsername() for UseDetails
    @Column(name = "username")
    private String apiQuestUsername;
    private String email;
    private String password;
    private int score;
    @Column(name = "name", table = "Roles")
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "role_id")
    private int roleId;

    @ManyToMany
    @JoinTable(
            name = "Users_APIs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "api_id")
    )
    private Set<API> apiSet = new HashSet<>();



    @Builder
    public User(int id, String apiQuestUsername, String password, String email, UserType userType,
                LocalDateTime createdAt, LocalDateTime updatedAt){
        super(createdAt, updatedAt);
        this.id = id;
        this.apiQuestUsername = apiQuestUsername;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }


    @Override
    public boolean equals(Object o){
        if(!(o instanceof User u)) return false;

        return this.id == u.id
                && this.apiQuestUsername.equals(u.apiQuestUsername)
                && this.email.equals(u.email)
                && this.userType.equals(u.userType)
                && this.password.equals(u.password)
                && super.equals(u);
    }


    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(
                new SimpleGrantedAuthority(getUserType().toString())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Getter
    public enum UserType{
        ROLE_ADMIN(1), ROLE_USER(2), ROLE_CONFIRMED_USER(3);

        private final int id;

        UserType(int id){this.id = id;}

    }
}
