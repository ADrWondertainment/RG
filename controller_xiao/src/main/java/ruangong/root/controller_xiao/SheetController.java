package ruangong.root.controller_xiao;


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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    @Resource
    private SheetService sheetService;
    @Resource
    private UserService userService;

    @Resource
    private AnswerService answerService;

    /*
    {
        "tid":1,
//        "uid":2,
//        "cid":1,
        "did":3,
        "name":"test",
        "description":"sample sheet",
        "start":"2022-05-15",
        "end":"2022-05-18",
//        "type":1,
//        "length":4
    }
    */


    @PostMapping
    public Result debutSheet(@RequestBody Sheet sheet, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer uid = (Integer) session.getAttribute("id");
        Integer cid = (Integer) session.getAttribute("cid");
        if (uid == null || uid == 0) {
            throw new BackException(ErrorCode.USER_ILLEGAL_ACCESS, "用户未登录");
        }
        return sheetService.fastCreateSheet(sheet, cid, uid);
    }


    /*
    {
        "pageNum":1
        "size":3
    }
     */

    @PostMapping("/sheetPages")
    public Result getSheetsInPages(@RequestBody String jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(JSONUtil.parseObj(jsonObject), httpServletRequest, userService);
        Integer uid = pageInfo.get("uid");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");
        return sheetService.getSheetsInPages(uid, pageIndex, sizePerPage);
    }

    @PostMapping("/answerPages")
    public Result showSheetAnswersInPages(@RequestBody String jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(JSONUtil.parseObj(jsonObject), httpServletRequest, userService);
        Integer uid = pageInfo.get("uid");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");
        return answerService.getAnswerInPagesByUserId(uid, pageIndex, sizePerPage);
    }

    /*
        {
            "id":1,
            "pass":0
        }
     */
    @PostMapping("/pass/show")
    public Result passSheetAnswers(@RequestBody String data) {

        JSONObject entries = JSONUtil.parseObj(data);
        Integer id = entries.get("id", Integer.class);
        Integer pass = entries.get("pass", Integer.class);

        return sheetService.checkSheetAnswer(id, pass);
    }


    /*
        {
            "sheetId":1,
            "mode":0               //0:checked,1:unchecked
        }
     */
    @PostMapping("/pass/check")
    public Result showCheckedOrUncheckedAnswers(@RequestBody String data) {

        return null;
    }



}
