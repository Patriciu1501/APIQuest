package bogatu.api.apiquest.dtos.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserInfo {

    private int id;
    private String username;
    private String email;
    @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
    @JsonProperty("registrationTime")
    private LocalDateTime createdAt;
    @JsonProperty("updateTime")
    @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;
}
