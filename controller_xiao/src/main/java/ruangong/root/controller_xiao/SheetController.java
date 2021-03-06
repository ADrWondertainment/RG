package ruangong.root.controller_xiao;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.*;
import ruangong.root.bean.Approve;
import ruangong.root.bean.CuserAstronaut;
import ruangong.root.bean.SpaceFederation;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.AnswerService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.service_xiao.PageUtil;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

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
    @Resource
    private SpaceFederation spaceFederation;
    @Resource
    private Result result;

    @Resource
    private TemplateService templateService;

    /**
     * {
     * "tid":1,
     * "did":3,
     * "name":"test",
     * "description":"sample sheet",
     * "start":"2022-05-15",
     * "end":"2022-05-18",
     * "order":
     * }
     */
    @PostMapping
    public Result debutSheet(@RequestBody String data, HttpServletRequest httpServletRequest) {
        Sheet sheet = JSONUtil.toBean(data, Sheet.class);
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

    private CuserAstronaut registeredAstronaut;

    /**
     * {
     * "index":1,
     * "pass":0
     * }
     */
    @PostMapping("/pass/check")
    public Result passSheetAnswers(@RequestBody String data) {

        if (registeredAstronaut == null) {
            return null;
        }
        registeredAstronaut.approve(data);

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "审批完成",
                null
        );

        return result;
    }


    @PostMapping("/pass/show")
    public Result showApproves(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer uid = (Integer) session.getAttribute("id");
        if (!spaceFederation.checkAstronauts(uid)) {
            return null;
        }
        registeredAstronaut = (CuserAstronaut) spaceFederation.getRegisteredAstronaut(uid);
        registeredAstronaut.getWork();
        List<Approve> workList = registeredAstronaut.getWorkList();
        JSONArray objects = JSONUtil.parseArray(workList);

        for (int i = 0; i < workList.size(); i++) {
            Approve temp = workList.get(i);

            Result sheetById = sheetService.getSheetById(temp.getSid());
            Sheet sheetFromData = ResultUtil.getBeanFromData(sheetById, Sheet.class);
            String sheetName = sheetFromData.getName();
            String sheetDescription = sheetFromData.getDescription();

            Result templateById = templateService.getTemplateById(sheetFromData.getTid());
            Template templateFromData = ResultUtil.getBeanFromData(templateById, Template.class);

            JsonBeanTemplate jsonBeanTemplate = JSONUtil.toBean(templateFromData.getData(), JsonBeanTemplate.class);
            JSONObject entries = JSONUtil.parseObj(jsonBeanTemplate);
            JSONArray originContent = JSONUtil.parseArray(entries.get("originContent"));

            JSONObject entries1 = objects.get(i, JSONObject.class).
                    putOnce("originContent", originContent).
                    putOnce("name", sheetName).
                    putOnce("description", sheetDescription);
            objects.set(i, entries1);
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "找到所有待审表单",
                objects
        );
        return result;

    }

    /**
     * {
     * "pageNum":1
     * "size":3
     * }
     */
    @PostMapping("/show/approves")
    public Result getAvailableApprovalSheets(HttpServletRequest httpServletRequest, @RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        Integer pageIndex = entries.get("pageNum", Integer.class);
        Integer sizePerPage = entries.get("size", Integer.class);
        Integer cid = (Integer) httpServletRequest.getSession().getAttribute("cid");
        JSONArray pages = sheetService.getApproveSheetsInPages(cid, pageIndex, sizePerPage);
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                pages
        );
        return result;
    }


}
