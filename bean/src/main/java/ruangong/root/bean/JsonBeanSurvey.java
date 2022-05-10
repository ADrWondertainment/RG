package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@Data
public class JsonBeanSurvey {
    private int sheetId;
    private List<JsonBeanSurveysAnswers> answers;
}
