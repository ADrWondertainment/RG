package ruangong.root.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class FrontException extends RuntimeException {

    private Integer errorCode;
    private String errorMessage;

}
