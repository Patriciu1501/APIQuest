package bogatu.api.apiquest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @Column(columnDefinition = "BIGSERIAL")
    @Id
    private int id;
    @Column(nullable = false)
    private String username;
    @Column
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private UserType userType;


    public enum UserType{
        VISITOR, REGISTERED, VERIFIED, ADMIN;
    }
}
