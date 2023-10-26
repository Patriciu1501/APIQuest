package bogatu.api.apiquest.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Map;

@Builder
public record ErrorMessage(HttpStatus httpStatus, String message, Collection<Map.Entry<String, String>> details) {

    public static final String INVALID_DATA_MESSAGE = "Invalid data provided" +
            ", please check and correct the following issues";


}
