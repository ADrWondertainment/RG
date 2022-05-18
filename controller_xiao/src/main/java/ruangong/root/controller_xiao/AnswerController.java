package ruangong.root.controller_xiao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.*;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.*;
import ruangong.root.utils.AnswerUtil;
import ruangong.root.utils.ResultUtil;
import ruangong.root.utils.TemplateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.HashMap;
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
    private UserService userService;

    @Resource
    private CuserService cuserService;

    @Resource
    private Result result;

    /*

        {
            "sheetId":7,
            "answers":[{"id": "0", "value": "A"}, {"id": "1", "value": ["5555"]}]
        }
     */
    @PostMapping("/pre")
    public Result preProcess(@RequestBody String data, HttpServletRequest httpServletRequest) {
        answer = AnswerUtil.strToAnswer(data);
        answerService.checkUserStatus(httpServletRequest);

        String email = (String) httpServletRequest.getSession().getAttribute("email");
        Result userByEmail = userService.GetUserByEmail(email);
        User userFromData = (User) userByEmail.getData();
        if (!answerService.checkUserCompany(userFromData, answer.getSid()))
            throw new FrontException(ErrorCode.ILLEGAL_COMPANY_USER, "该用户不是公司用户");

        int sid = answer.getSid();
        Result sheetById = sheetService.getSheetById(sid);
        Sheet sheetFromData = ResultUtil.getBeanFromData(sheetById, Sheet.class);
        Result templateById = templateService.getTemplateById(sheetFromData.getTid());
        Template templateFromData = ResultUtil.getBeanFromData(templateById, Template.class);


        JSONArray jsonArray = JSONUtil.parseArray(userFromData.getSheets());
        List<String> strings = JSONUtil.toList(jsonArray, String.class);

        for (String str : strings) {

            Result result1 = answerService.selectAnswerByAnswerID(Integer.parseInt(str));
            Answer answerFromData = ResultUtil.getBeanFromData(result1, Answer.class);
            if (answerFromData.getSid().equals(answer.getSid())) {
                answerService.checkAnswerStatus(answerFromData);
                Object data1 = answerFromData.getData();
                JSONObject entries = JSONUtil.createObj().putOnce("unfinished", data1).putOnce("template", templateFromData.getData());
                String s = JSONUtil.toJsonPrettyStr(entries);
                ResultUtil.quickSet(
                        result,
                        ErrorCode.ALL_SET,
                        "找到用户未完成答案",
                        s
                );
                return result;
            }

        }

        JSONObject entries2 = JSONUtil.createObj().putOnce("unfinished", null).putOnce("template", templateFromData.getData());

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "用户第一次填写该问卷",
                JSONUtil.toJsonPrettyStr(entries2)
        );
        return result;
    }

    /*


        {
            "sheetId":7,
            "answers":[{"id": "0", "value": "A"}, {"id": "1", "value": ["5555"]}]
        }

     */

    @PostMapping("/submit")
    public Result collectAnswer(@RequestBody String data, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer uid = (Integer) session.getAttribute("id");
        Answer strToAnswer = AnswerUtil.strToAnswer(data);
        User userByEmail = userService.getUserByEmail((String) session.getAttribute("email"));
        strToAnswer.setUid(uid);
        result = answerService.insertAnswer(strToAnswer, userByEmail);
        return result;

    }

    @PostMapping("/save")
    public Result saveAnswer(@RequestBody String data, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer uid = (Integer) session.getAttribute("uid");
        Answer strToAnswer = AnswerUtil.strToAnswer(data);
        result = answerService.saveTempAnswer(strToAnswer);
        return result;

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
                        if (tempContent.getType().equals("input")) {
                            tempContent.getValue().put(tempAnswer.getValue(), 0);
                        } else {
                            String index = tempAnswer.getValue();
                            Map<String, Integer> value = tempContent.getValue();
                            Integer count = value.get(index) + 1;
                            value.put(index, count);
                        }


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
                    JSONUtil.toJsonPrettyStr(template.getData())
            );

        }


        return result;
    }

    /*
    {
        "pageNum":1,
        "size":5
    }
     */

    @GetMapping()
    public Result getAnswersInPage(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(jsonObject, request, userService);

        return answerService.getAnswersByUserID(pageInfo.get("id"), pageInfo.get("pageIndex"), pageInfo.get("sizePerPage"));


    }


}
