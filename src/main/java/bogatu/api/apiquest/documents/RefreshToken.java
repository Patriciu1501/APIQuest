package bogatu.api.apiquest.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@AllArgsConstructor
@Builder
@Document(collection = "refreshTokens")
@Getter
@Setter
@ToString
public class RefreshToken {

    @Id
    private String value;
    @Builder.Default
    private boolean valid = true;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
