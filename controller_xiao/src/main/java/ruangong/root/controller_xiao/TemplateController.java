package ruangong.root.controller_xiao;


import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.service_xiao.TemplateService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Resource
    private Result result;

    @Resource
    private TemplateService templateService;

    @PostMapping
    public Result createOrUpdateTemplate(@RequestBody Template template) {
        result = templateService.createOrUpdateTemplateByBean(template);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteTemplateById(@PathVariable String id) {
        result = templateService.deleteTemplateById(Integer.parseInt(id));
        return result;
    }

    @GetMapping
    public void getTemplatesInPages(@RequestBody JSONObject jsonObject) {
        Integer id = (Integer) jsonObject.get("id");
        Integer pageNum = (Integer) jsonObject.get("pageNum");
        Integer size = (Integer) jsonObject.get("size");

    }


}
