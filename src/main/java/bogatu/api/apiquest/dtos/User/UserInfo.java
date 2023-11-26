package bogatu.api.apiquest.dtos.User;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.API;
import bogatu.api.apiquest.entities.Role;
import bogatu.api.apiquest.entities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "username", "email", "createdAt", "updatedAt"})
public class UserInfo {

    private int id;
    private String username;
    private String email;
    private int score;
    @JsonProperty("type")
    private String role;
    @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
    @JsonProperty("registrationTime")
    private LocalDateTime createdAt;
    @JsonProperty("updateTime")
    @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;
    @JsonProperty("APIs")
    private Set<APIDto> apiSet;
}
