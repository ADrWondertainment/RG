package ruangong.root.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FrontException extends RuntimeException {

    public FrontException() {
    }

    private Integer errorCode;

    public FrontException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public FrontException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FrontException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public FrontException(Integer errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
}
