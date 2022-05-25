package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 *
 * @author pangx
 */
@Data
@Component
@Scope("prototype")
public class Result {
    private Integer errorCode;
    private String message;
    private Object data;
}
