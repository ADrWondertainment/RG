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
public class JsonBeanSurvey {
    private int sheetId;
    private Transfer1 answers;
    private int done;
    private int pass;
    private String flow;
}
