package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
public class Transfer1 {
    private List<JsonBeanSurveysAnswers> data;
    private Integer done;
    private Integer pass;
    private String flow;

}
