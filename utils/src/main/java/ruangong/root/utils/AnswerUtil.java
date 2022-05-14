package ruangong.root.utils;

import cn.hutool.json.JSONUtil;
import ruangong.root.bean.Answer;
import ruangong.root.bean.JsonBeanSurvey;

public class AnswerUtil {

    public static Answer jsonBeanToAnswer(JsonBeanSurvey jsonBeanSurvey) {
        Answer answer = new Answer();
        answer.setSheetId(jsonBeanSurvey.getSheetId());
        answer.setData(JSONUtil.toJsonPrettyStr(jsonBeanSurvey.getAnswers()));

        return answer;
    }

    public static Answer strToAnswer(String str) {

        return jsonBeanToAnswer(JSONUtil.toBean(str, JsonBeanSurvey.class));
    }


}
