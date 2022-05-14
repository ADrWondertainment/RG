package ruangong.root.controller_xiao;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.JsonBeanTemplate;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.bean.User;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.utils.PageUtil;
import ruangong.root.utils.TemplateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

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

    @PostMapping
    public Result createOrUpdateTemplate(@RequestBody String data, HttpServletRequest request) {

        JSONObject entries = JSONUtil.parseObj(data);
        String json = (String) entries.get("json");

        System.out.println(JSONUtil.toJsonStr(json));

        jsonBeanTemplate = JSONUtil.toBean(json, JsonBeanTemplate.class);
        template = TemplateUtil.strToTemplate(json);
        String email = (String) request.getSession().getAttribute("email");
        Result result1 = userService.GetUserByEmail(email);

        User user = (User) result1.getData();
        template.setUid(user.getId());
        jsonBeanTemplate.setUid(user.getId());
        template.setData(JSONUtil.toJsonPrettyStr(jsonBeanTemplate));

        result = templateService.createOrUpdateTemplateByBean(template, jsonBeanTemplate);

        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteTemplateById(@PathVariable String id) {
        result = templateService.deleteTemplateById(Integer.parseInt(id));
        return result;
    }

    /*
    {
        "pageNum":1,
        "size":5
    }
     */
    @GetMapping
    public Result getTemplatesInPages(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(jsonObject, request, userService);

        return templateService.getTemplatesInPages(pageInfo.get("id"), pageInfo.get("pageIndex"), pageInfo.get("sizePerPage"));
    }


}
