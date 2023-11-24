package bogatu.api.apiquest.dtos.API;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"name", "endpoint", "score", "isDefault", "createdAt", "updatedAt"})
public record APIDto(String name,
                     String endpoint,
                     int score,
                     boolean isDefault,
                     @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
                     @JsonInclude(JsonInclude.Include.NON_NULL)
                     @JsonProperty("registrationTime")
                     LocalDateTime createdAt,
                     @JsonFormat(pattern = "dd MMM, YYYY 'at' HH:mm")
                     @JsonInclude(JsonInclude.Include.NON_NULL)
                     @JsonProperty("updateTime")
                     LocalDateTime updatedAt) {


    public APIDto{
        createdAt = LocalDateTime.now();
    }

}
