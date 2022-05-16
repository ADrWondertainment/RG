package ruangong.root.controller_xiao;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.*;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.service_xiao.PageUtil;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    @Resource
    private SheetService sheetService;
    @Resource
    private UserService userService;

    @Resource
    private AnswerService answerService;

    @Resource
    private Result result;

    /*
    {
        "tid":1,
        "uid":2,
        "name":"test",
        "description":"sample sheet",
        "start":"2022-05-15",
        "end":"2022-05-18",
        "type":1,
        "length":4
    }
    */
    @PostMapping
    public Result debutSheet(@RequestBody Sheet sheet, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getSession().getAttribute("email");
        if (email == null) {
            throw new BackException(ErrorCode.USER_ILLEGAL_ACCESS, "用户未登录");
        }
        Result result = userService.GetUserByEmail(email);
        User user = (User) result.getData();

        return sheetService.fastCreateSheet(sheet, user.getId());
    }


    @PostMapping("/url")
    public Result enableSheetUrl(@RequestBody UrlResourcedLocation urlResourcedLocation) {
        return sheetService.updateLocatedUrl(urlResourcedLocation);

    }

    @GetMapping
    public Result getSheetsInPages(@RequestBody JSONObject jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(jsonObject, httpServletRequest, userService);
        Integer id = pageInfo.get("id");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");


        return sheetService.getSheetsInPages(id, pageIndex, sizePerPage);
    }

    @PostMapping("/pass/{id}")
    public Result showSheetAnswers(@PathVariable String id) {
        Result answersBySheetId = answerService.getAnswersBySheetId(Integer.parseInt(id));
        String data = (String) answersBySheetId.getData();
        JSONArray objects = JSONUtil.parseArray(data);
        List<Answer> answers = JSONUtil.toList(objects, Answer.class);

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(answers)
        );

        return result;
    }

    /*
        {
            "id":1,
            "pass":0
        }
     */
    @PostMapping("/pass")
    public Result passSheetAnswers(@RequestBody String data) {

        JSONObject entries = JSONUtil.parseObj(data);
        Integer id = entries.get("id", Integer.class);
        Integer pass = entries.get("pass", Integer.class);

        return sheetService.checkSheetAnswer(id, pass);
    }

}
