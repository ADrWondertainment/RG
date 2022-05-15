package ruangong.root.service_xiao.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.utils.ResultUtil;

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

    @Resource
    private AnswerMapper answerMapper;

    @Override
    public Result collectAnswerAndUpdate(JsonBeanSurvey jsonBeanSurvey) {

        int sheetId = jsonBeanSurvey.getSheetId();

        Result result1 = sheetService.getSheetById(sheetId);

        Sheet sheet1 = JSONUtil.toBean((String) result1.getData(), Sheet.class);

        JsonBeanTemplate jsonBeanTemplate = JSONUtil.toBean(sheet1.getLocation(), JsonBeanTemplate.class);

        int length = jsonBeanTemplate.getContentLength();

        List<JsonBeanTemplateContentsContent> content = jsonBeanTemplate.getContent();

        List<JsonBeanSurveysAnswers> answers = jsonBeanSurvey.getAnswers();

//        for (int i = 0; i < length; i++) {
//
//            JsonBeanTemplateContentsContent tempContent = content.get(i);
//            JsonBeanSurveysAnswers tempAnswer = answers.get(i);
//
//            String index = tempAnswer.getValue();
//            Map<String, Integer> value = tempContent.getValue();
//            Integer count = value.get(index) + 1;
//            value.put(index, count);
//
//        }

        sheet1.setLocation(JSONUtil.toJsonPrettyStr(jsonBeanTemplate));

        result = sheetService.updateSheet(sheet1);


        return result;
    }

    @Override
    public Result insertAnswer(Answer answer) {

        int insert = answerMapper.insert(answer);

        if (insert != 1) {
            throw new BackException(ErrorCode.ANSWER_INSERT_FAILURE, "回答插入失败");
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "答案提交成功",
                1
        );

        return null;
    }

    @Override
    public Result getAnswersBySheetId(Integer sheetId) {

        QueryWrapper<Answer> query = Wrappers.query();
        query.eq("sid", sheetId);
        List<Answer> answers = answerMapper.selectList(query);

        if (answers.size() == 0) {
            throw new BackException(ErrorCode.ANSWER_SELECT_FAILURE, "回答分页查找失败或者无人回答");
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "成功获取调查信息",
                JSONUtil.toJsonStr(answers)
        );

        return result;
    }
}
