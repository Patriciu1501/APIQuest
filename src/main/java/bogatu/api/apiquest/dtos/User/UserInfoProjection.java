package bogatu.api.apiquest.dtos.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

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
}
