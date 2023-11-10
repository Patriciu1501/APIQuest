package bogatu.api.apiquest.dtos.User;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.API;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.web.bind.annotation.Mapping;

import java.time.LocalDateTime;
import java.util.Set;

public interface UserInfoProjection {

    int getId();
    String getUsername();
    String getEmail();

    @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
    @JsonProperty("registrationTime")
    LocalDateTime getCreatedAt();

    @JsonProperty("updateTime")
    @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    LocalDateTime getUpdatedAt();

    @JsonProperty("APIs")
    Set<APIDto> getApiSet();

}
