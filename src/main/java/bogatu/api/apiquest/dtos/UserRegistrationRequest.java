package bogatu.api.apiquest.dtos;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record UserRegistrationRequest (@Size(min = 5, message = "The username length should be at least 5")
                                       String username,
                                       @Pattern(
                                               regexp = "^(?=.*[A-Za-z])(?=.*\\d).{10,}$",
                                               message = """
                                                       The password must have the min length 10
                                                       and it must contain both letters and numbers"""
                                       )
                                       String password,
                                       @Email(message = "Not valid email format") String email){
}
