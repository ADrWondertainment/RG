package ruangong.root.controller_xiao;

import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Answer;
import ruangong.root.bean.JsonBeanSurvey;
import ruangong.root.bean.Result;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.utils.AnswerUtil;

import javax.annotation.Resource;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Resource
    private Answer answer;

    @Resource
    private AnswerService answerService;

    @Resource
    private Result result;

    public Result collectAnswers(String data) {

        answer = AnswerUtil.strToAnswer(data);
        return answerService.insertAnswer(answer);
    }


}
