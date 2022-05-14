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
import ruangong.root.utils.TemplateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

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

        result = templateService.createOrUpdateTemplateByBean(template, jsonBeanTemplate);

        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteTemplateById(@PathVariable String id) {
        result = templateService.deleteTemplateById(Integer.parseInt(id));
        return result;
    }

    @GetMapping
    public Result getTemplatesInPages(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Result result = userService.GetUserByEmail(email);

        User user = (User) result.getData();

        Integer id = user.getId();
        Integer pageNum = (Integer) jsonObject.get("pageNum");
        Integer size = (Integer) jsonObject.get("size");

        return templateService.getTemplatesInPages(id, pageNum, size);
    }


}
