package ruangong.root.service_xiao;

import org.springframework.stereotype.Service;
import ruangong.root.bean.Answer;
import ruangong.root.bean.JsonBeanSurvey;
import ruangong.root.bean.Result;

@Service
public interface AnswerService {

    public Result collectAnswerAndUpdate(JsonBeanSurvey jsonBeanSurvey);

    public Result insertAnswer(Answer answer);

    public Result getAnswersBySheetId(Integer sheetId);

    public boolean checkAnswerTime(Answer answer);

    public boolean closeAnswer(Answer answer);

    public Result saveTempAnswer(Answer answer);
}
