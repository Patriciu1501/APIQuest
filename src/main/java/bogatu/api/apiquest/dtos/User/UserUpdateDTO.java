package bogatu.api.apiquest.dtos.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record UserUpdateDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer id,
                            @Size(min = 5)
                            @JsonProperty("username")
                            String apiQuestUsername,
                            @Pattern(
                                    regexp = "^(?=.*[A-Za-z])(?=.*\\d).{10,}$",
                                    message = "The password must have min length 10 " +
                                            "and must contains both letters and digits"
                            )
                            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password,
                            @Email String email,
                            @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
                            @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "updateTime")
                            LocalDateTime updatedAt) {
}
