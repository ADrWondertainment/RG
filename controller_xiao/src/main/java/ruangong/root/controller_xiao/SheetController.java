package ruangong.root.controller_xiao;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.*;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.service_xiao.PageUtil;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author pangx
 */
@RestController
@RequestMapping("/sheets")
public class SheetController {

    @Resource
    private SheetService sheetService;
    @Resource
    private UserService userService;
    @Resource
    private AnswerService answerService;


    /**
     * {
     * "tid":1,
     * "did":3,
     * "name":"test",
     * "description":"sample sheet",
     * "start":"2022-05-15",
     * "end":"2022-05-18",
     * }
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


    /**
     * {
     * "pageNum":1
     * "size":3
     * }
     */
    @PostMapping("/all")
    public Result getSheetsInPages(@RequestBody String jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(JSONUtil.parseObj(jsonObject), httpServletRequest, userService);
        Integer uid = pageInfo.get("uid");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");
        return sheetService.getSheetsInPages(uid, pageIndex, sizePerPage);
    }
    @PostMapping("/query")
    public Result getQueriesInPages(@RequestBody String jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(JSONUtil.parseObj(jsonObject), httpServletRequest, userService);
        Integer uid = pageInfo.get("uid");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");
        return sheetService.getQueriesInPages(uid, pageIndex, sizePerPage);
    }

    @PostMapping("/approval")
    public Result getApprovalsInPages(@RequestBody String jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(JSONUtil.parseObj(jsonObject), httpServletRequest, userService);
        Integer uid = pageInfo.get("uid");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");
        return sheetService.getApprovalsInPages(uid, pageIndex, sizePerPage);
    }

    @PostMapping("/answer")
    public Result showSheetAnswersInPages(@RequestBody String jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(JSONUtil.parseObj(jsonObject), httpServletRequest, userService);
        Integer uid = pageInfo.get("uid");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");
        return answerService.getAnswerInPagesByUserId(uid, pageIndex, sizePerPage);
    }

    /**
     * {
     * "id":1,
     * "pass":0
     * }
     */
    @PostMapping("/pass/check")
    public Result passSheetAnswers(@RequestBody String data) {

        JSONObject entries = JSONUtil.parseObj(data);
        Integer id = entries.get("id", Integer.class);
        Integer pass = entries.get("pass", Integer.class);

        return sheetService.checkSheetAnswer(id, pass);
    }


    /**
     * {
     * "sheetId":1,
     * "pageIndex":1,
     * "size":4,
     * "mode":0,               //1:unchecked, 2:通过, 3:不通过
     * }
     */
    @PostMapping("/pass/show")
    public Result showCheckedOrUncheckedAnswers(@RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        Integer sheetId = (Integer) entries.get("sheetId");
        Integer mode = (Integer) entries.get("mode");

        Integer pageIndex = (Integer) entries.get("pageIndex");
        Integer size = (Integer) entries.get("size");
        Integer[] ids = {sheetId, mode, 1};
        String[] columns = {"sid", "pass", "done"};

        return answerService.getCheckingAnswers(ids, pageIndex, size, columns);

    }


}
