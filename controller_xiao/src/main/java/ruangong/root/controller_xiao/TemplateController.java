package ruangong.root.controller_xiao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.service_xiao.TemplateService;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    private Result result;

    @Autowired
    private TemplateService templateService;

    @PostMapping
    public Result createTemplate(@RequestBody Template template){
        result = templateService.createTemplateByBean(template);
        return result;
    }


}
