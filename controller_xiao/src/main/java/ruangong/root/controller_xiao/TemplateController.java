package ruangong.root.controller_xiao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Result;
import ruangong.root.exception.BackException;
import ruangong.root.exception.FrontException;
import ruangong.root.exception.UnknownException;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    private Result result;



}
