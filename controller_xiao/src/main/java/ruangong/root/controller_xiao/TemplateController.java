package ruangong.root.controller_xiao;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.JsonBeanTemplate;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.bean.User;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.service_xiao.PageUtil;
import ruangong.root.utils.TemplateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * @author pangx
 */
@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Resource
    private Result result;

    @Resource
    private TemplateService templateService;

    @Resource
    private UserService userService;

    @Resource
    private Template template;

    @Resource
    private JsonBeanTemplate jsonBeanTemplate;


    @PostMapping("/modify")
    public Result modifyTemplate() {

        return null;
    }


    @PostMapping("/create")
    public Result createOrUpdateTemplate(@RequestBody String data, HttpServletRequest request) {


        JSONObject entries = JSONUtil.parseObj(data);
        String json = (String) entries.get("json");
        if (json == null) {
            throw new FrontException(ErrorCode.FRONT_DATA_IRREGULAR, "前端数据格式未按照规范");
        }

        try {
            jsonBeanTemplate = JSONUtil.toBean(json, JsonBeanTemplate.class);
            template = TemplateUtil.strToTemplate(json);

        } catch (Exception e) {
            throw new BackException(ErrorCode.UTIL_ERROR, "工具类出错");
        }

        Integer uid = (Integer) request.getSession().getAttribute("id");
        if (uid == null || uid == 0) {
            throw new BackException(ErrorCode.USER_ILLEGAL_ACCESS, "用户未登录");
        }

        template.setUid(uid);
        jsonBeanTemplate.setUid(uid);
        template.setData(JSONUtil.toJsonPrettyStr(jsonBeanTemplate));
        result = templateService.createOrUpdateTemplateByBean(template, jsonBeanTemplate);

        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteTemplateById(@PathVariable String id) {
        return templateService.deleteTemplateById(Integer.parseInt(id));
    }

    /**
     * {
     * "pageNum":1,
     * "size":5
     * }
     */
    @PostMapping("/get")
    public Result getTemplatesInPages(@RequestBody String jsonObject, HttpServletRequest request) {
        JSONObject entries = JSONUtil.parseObj(jsonObject);
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(entries, request, userService);
        return templateService.getTemplatesInPages(pageInfo.get("uid"), pageInfo.get("pageIndex"), pageInfo.get("sizePerPage"));
    }


}
