package learningtask.learningnew.dto;

import java.io.Serializable;
import java.util.Map;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude (JsonInclude.Include.NON_EMPTY)
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int statusCode;

    private String message;

    @JsonIgnore
    private HttpStatus httpStatus;

    private Map<String, String> errors;

    public ErrorResponse(int statusCode, HttpStatus httpStatus, String message) {
        super();
        this.message = message;
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

}
