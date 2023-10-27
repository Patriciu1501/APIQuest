package bogatu.api.apiquest.dtos.User;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserRegistrationResponse(int id, String username, String email,

                                       @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm:ss")
                                       LocalDateTime createdAt) {

}
