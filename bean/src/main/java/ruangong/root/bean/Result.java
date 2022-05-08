package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class Result {
    private Integer errorCode;
    private String message;
    private Object data;
}
