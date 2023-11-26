package bogatu.api.apiquest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(columnDefinition = "INT4")
    private long id;
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public static Role getUserRole(){
        return new Role(UserType.ROLE_USER.id, UserType.ROLE_USER.toString(), null);
    }


    public enum UserType{
        ROLE_ADMIN(1), ROLE_USER(2), ROLE_CONFIRMED_USER(3);

        private final int id;

        UserType(int id){this.id = id;}

    }
}
