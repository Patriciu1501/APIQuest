package bogatu.api.apiquest.exceptions;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestValidationException extends RuntimeException{

    private Map<String, String> fieldDetails;
    public RequestValidationException(String message){super(message);}
    public RequestValidationException(Map<String, String> fieldDetails){this.fieldDetails = fieldDetails;}
    public RequestValidationException(String message, Map<String, String> fieldDetails){
        super(message);
        this.fieldDetails = fieldDetails;
    }
}
