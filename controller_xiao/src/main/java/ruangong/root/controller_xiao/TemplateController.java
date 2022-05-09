package ruangong.root.controller_xiao;


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
    public Result createOrUpdateTemplate(@RequestBody Template template){
        result = templateService.createTemplateByBean(template);
        return result;
    }

    @DeleteMapping("/{id}")
    public Result deleteTemplateById(@PathVariable String id){
        result = templateService.deleteTemplateById(Integer.parseInt(id));
        return result;
    }




}
