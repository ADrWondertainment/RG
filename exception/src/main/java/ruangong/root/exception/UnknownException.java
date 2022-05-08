package ruangong.root.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UnknownException extends RuntimeException{
    private Integer errorCode;

    public UnknownException() {
    }

    public UnknownException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public UnknownException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public UnknownException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UnknownException(Integer errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

}

