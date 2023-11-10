package bogatu.api.apiquest.dtos.User;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonPropertyOrder({"id", "username", "email", "createdAt"})
public record UserRegistrationResponse(int id, String username, String email,
                                       @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
                                       @JsonProperty("registrationTime")
                                       LocalDateTime createdAt) {
}
