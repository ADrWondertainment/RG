package ruangong.root.controller_xiao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.*;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.utils.AnswerUtil;
import ruangong.root.utils.ResultUtil;
import ruangong.root.utils.TemplateUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Resource
    private Answer answer;

    @Resource
    private Sheet sheet;

    @Resource
    private Template template;

    @Resource
    private AnswerService answerService;

    @Resource
    private SheetService sheetService;

    @Resource
    private TemplateService templateService;

    @Resource
    private Result result;

    /*

        {
            "sheetId":7,
            "answers":[{"id": "0", "value": "A"}, {"id": "1", "value": ["5555"]}]
        }
     */
    @PostMapping("/submit")
    public Result collectAnswers(@RequestBody String data) {
        answer = AnswerUtil.strToAnswer(data);
        return answerService.insertAnswer(answer);
    }


    @GetMapping("/result/{id}")
    public Result returnSheetResultById(@PathVariable String id) {

        int sheetId = Integer.parseInt(id);

        Result sheetById = sheetService.getSheetById(sheetId);
        sheet = ResultUtil.getBeanFromData(sheetById, Sheet.class);

        if (sheet.getType() == 0) {
            Integer tid = sheet.getTid();
            Result templateById = templateService.getTemplateById(tid);
            template = ResultUtil.getBeanFromData(templateById, Template.class);

            Integer length = template.getLength();

            List<JsonBeanTemplateContentsContent> content = JSONUtil.toBean(template.getData(), JsonBeanTemplate.class).getContent();


            Result answersBySheetId = answerService.getAnswersBySheetId(sheetId);

            String data1 = (String) answersBySheetId.getData();

            List<Answer> list = JSONUtil.toList(data1, Answer.class);


//        AnswerList answerList = ResultUtil.getBeanFromData(answersBySheetId, AnswerList.class);
//
//        List<Answer> list = answerList.getList();

            try {
                for (Answer o : list) {
                    String data = o.getData();
//                JSONArray parseArray = JSONUtil.parseArray((String) JSONUtil.parseObj(data).get("content"));
                    JSONArray content1 = JSONUtil.parseArray(data);
                    List<JsonBeanSurveysAnswers> answers = JSONUtil.toList(content1, JsonBeanSurveysAnswers.class);
                    for (int t = 0; t < length; t++) {

                        JsonBeanTemplateContentsContent tempContent = content.get(t);
                        JsonBeanSurveysAnswers tempAnswer = answers.get(t);

                        String index = tempAnswer.getValue();
                        Map<String, Integer> value = tempContent.getValue();
                        Integer count = value.get(index) + 1;
                        value.put(index, count);

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new BackException(ErrorCode.ANSWER_PROCESS_FAILURE, "处理回答数据时出错");
            }


            ResultUtil.quickSet(
                    result,
                    ErrorCode.ALL_SET,
                    "问卷结果返回成功",
                    JSONUtil.toJsonPrettyStr(content)
            );

        }


        return result;
    }

    @PostMapping("/save")
    public Result saveTempAnswer(@RequestBody String data) {
        answer = AnswerUtil.strToAnswer(data);
        return answerService.saveTempAnswer(answer);

    }


}
