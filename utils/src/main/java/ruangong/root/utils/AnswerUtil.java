package ruangong.root.utils;

import cn.hutool.json.JSONUtil;
import ruangong.root.bean.Answer;
import ruangong.root.bean.JsonBeanSurvey;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;

public class AnswerUtil {

    public static Answer jsonBeanToAnswer(JsonBeanSurvey jsonBeanSurvey) {
        Answer answer = new Answer();
        answer.setSid(jsonBeanSurvey.getSheetId());
        answer.setData(JSONUtil.toJsonPrettyStr(jsonBeanSurvey.getAnswers()));

        return answer;
    }

    public static Answer strToAnswer(String str) {

        JsonBeanSurvey jsonBeanSurvey = JSONUtil.toBean(str, JsonBeanSurvey.class);
        if (jsonBeanSurvey == null) {
            throw new FrontException(ErrorCode.FRONT_DATA_IRREGULAR, "前端数据格式不规范");
        }
        return jsonBeanToAnswer(jsonBeanSurvey);
    }


}
