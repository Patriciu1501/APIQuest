package bogatu.api.apiquest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GenericEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    @JsonIgnore
    private LocalDateTime updatedAt;


    @Override
    public boolean equals(Object o){
        if(!(o instanceof GenericEntity g)) return false;

        return Objects.equals(this.createdAt, g.createdAt)
                && Objects.equals(this.updatedAt, g.updatedAt);
    }
}
