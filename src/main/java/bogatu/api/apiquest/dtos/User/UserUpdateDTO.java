package bogatu.api.apiquest.dtos.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record UserUpdateDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer id,
                            @Size(min = 5) String username,
                            @Pattern(
                                    regexp = "^(?=.*[A-Za-z])(?=.*\\d).{10,}$",
                                    message = "The password must have min length 10 " +
                                            "and must contains both letters and numbers"
                            )
                            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password,
                            @Email String email,
                            @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "updateTime")
                            LocalDateTime updatedAt) {
}
