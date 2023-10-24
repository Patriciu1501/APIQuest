package bogatu.api.apiquest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Users")
@Builder
@NoArgsConstructor
@Getter
@Setter
public class User {

    @SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", allocationSize = 1)
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
