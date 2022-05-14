package ruangong.root.controller_xiao;

import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.JsonBeanSurvey;
import ruangong.root.bean.Result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Resource
    private Result result;



}
