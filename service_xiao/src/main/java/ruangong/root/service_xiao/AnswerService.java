package ruangong.root.service_xiao;

import org.springframework.stereotype.Service;
import ruangong.root.bean.JsonBeanSurvey;
import ruangong.root.bean.Result;

@Service
public interface AnswerService {

    public Result collectAnswerAndUpdate(JsonBeanSurvey jsonBeanSurvey);

}
