package bogatu.api.apiquest.dtos;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record UserRegistrationRequest (@NotNull @Size(min = 5)
                                       String username,
                                       @NotNull @Pattern(
                                               regexp = "^(?=.*[A-Za-z])(?=.*\\d).{10,}$",
                                               message = "The password must have min length 10 " +
                                                       "and must contains both letters and numbers"
                                       )
                                       String password,
                                       @NotNull @NotBlank @Email String email){
}
