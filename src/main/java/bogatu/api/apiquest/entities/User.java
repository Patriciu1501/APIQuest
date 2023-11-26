package bogatu.api.apiquest.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
@AllArgsConstructor
@SuperBuilder
public class User extends GenericEntity{

    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @Column(columnDefinition = "BIGSERIAL")
    @Id
    private int id;
    private String username;
    private String email;
    private String password;
    private int score;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @Builder.Default
    private Role role = Role.getUserRole();

    @ManyToMany
    @JoinTable(
            name = "Users_APIs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "api_id")
    )

    @Builder.Default
    private Set<API> apiSet = new HashSet<>();


    @Override
    public boolean equals(Object o){
        if(!(o instanceof User u)) return false;

        return this.id == u.id
                && this.email.equals(u.email);
    }
}
