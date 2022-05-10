package ruangong.root.service_xiao.impl;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.SheetService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    @Resource
    private Result result;

    @Resource
    private SheetService sheetService;

    @Override
    public Result collectAnswerAndUpdate(JsonBeanSurvey jsonBeanSurvey) {

        int sheetId = jsonBeanSurvey.getSheetId();

        Result result1 = sheetService.getSheetById(sheetId);

        Sheet sheet1 = JSONUtil.toBean((String) result1.getData(), Sheet.class);

        JsonBeanTemplate jsonBeanTemplate = JSONUtil.toBean(sheet1.getData(), JsonBeanTemplate.class);

        int length = jsonBeanTemplate.getContentLength();

        List<JsonBeanTemplateContentsContent> content = jsonBeanTemplate.getContent();

        List<JsonBeanSurveysAnswers> answers = jsonBeanSurvey.getAnswers();

        for (int i = 0; i < length; i++) {

            JsonBeanTemplateContentsContent tempContent = content.get(i);
            JsonBeanSurveysAnswers tempAnswer = answers.get(i);

            String index = tempAnswer.getValue();
            Map<String, Integer> value = tempContent.getValue();
            Integer count = value.get(index) + 1;
            value.put(index, count);

        }

        sheet1.setData(JSONUtil.toJsonPrettyStr(jsonBeanTemplate));

        result = sheetService.updateSheet(sheet1);


        return result;
    }
}
