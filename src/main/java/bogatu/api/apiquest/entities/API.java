package bogatu.api.apiquest.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "APIs")
@NoArgsConstructor
@Getter
@Setter
@NamedQuery(name = "API.getAllAPIs",
        query = """
            SELECT new bogatu.api.apiquest.dtos.API.APIDto(a.name, a.endpoint, a.createdAt, a.updatedAt)
            FROM API a""")
public class API extends GenericEntity{

    @SequenceGenerator(name = "api_id_generator", sequenceName = "apis_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_id_generator")
    @Id
    @Column(columnDefinition = "BIGSERIAL")
    private int id;
    private String name;
    private String endpoint;


    @ManyToMany(mappedBy = "apiSet")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @Builder
    public API(int id, String name, String endpoint, LocalDateTime createdAt, LocalDateTime updatedAt){
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.endpoint = endpoint;
    }

}
