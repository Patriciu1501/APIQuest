package bogatu.api.apiquest.dtos.User;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record UserRegistrationRequest (@NotNull @Size(min = 5)
                                       String username,
                                       @NotNull @Pattern(
                                               regexp = "^(?=.*[A-Za-z])(?=.*\\d).{10,}$",
                                               message = "The password must have min length 10 " +
                                                       "and must contains both letters and numbers"
                                       )
                                       String password,
                                       @NotNull @NotBlank @Email String email,
                                       LocalDateTime createdAt){


    public UserRegistrationRequest{
        createdAt = LocalDateTime.now();
    }
}
