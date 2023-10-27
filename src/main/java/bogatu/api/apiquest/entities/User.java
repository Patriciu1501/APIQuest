package bogatu.api.apiquest.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
@ToString
public class User extends GenericEntity{

    @SequenceGenerator(name = "user_id_generator", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @Column(columnDefinition = "BIGSERIAL")
    @Id
    private int id;

    private String username;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private UserType userType;

    @Builder
    public User(int id, String username, String password, UserType userType, String email,
                LocalDateTime createdAt, LocalDateTime updatedAt){
        super(createdAt, updatedAt);
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public enum UserType{
        VISITOR, REGISTERED, VERIFIED, ADMIN
    }


    @Override
    public boolean equals(Object o){
        if(!(o instanceof User u)) return false;

        return this.id == u.id
                && this.username.equals(u.username)
                && this.email.equals(u.email)
                && this.userType.equals(u.userType)
                && this.password.equals(u.password)
                && super.equals(u);

    }
}
