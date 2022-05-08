package ruangong.root.exception;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BackException extends RuntimeException{
    private Integer errorCode;

    public BackException() {
    }

    public BackException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public BackException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BackException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BackException(Integer errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

}
