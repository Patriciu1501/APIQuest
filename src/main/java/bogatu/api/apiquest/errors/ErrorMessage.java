package bogatu.api.apiquest.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Builder
@Getter // unless you want an exception, it is mandatory for the class representing the json body to have getters
public final class ErrorMessage {

    public static final String INVALID_DATA_MESSAGE = "Invalid data provided" +
                                                         ", please check and correct the following issues";


    private final HttpStatus httpStatus;
    private final String message;
    private final Collection<Map.Entry<String, String>> details;
}
