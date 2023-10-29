package bogatu.api.apiquest.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Map;

@Builder
@Getter
public class ErrorMessage {

    private final HttpStatus httpStatus;
    @Builder.Default
    private final String message = "Invalid data. Correct the following issues";
    private final Collection<Map.Entry<String, String>> details;
}
