package learningtask.learningnew.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDefineExceptions extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorResponse errorResponse;

    public UserDefineExceptions() {
        super();
    }

    public UserDefineExceptions(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public UserDefineExceptions(String message) {
        super(message);
    }
}