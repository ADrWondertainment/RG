package ruangong.root.service_xiao;


import org.springframework.stereotype.Service;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;

@Service
public interface TemplateService {

    public Result createTemplateByBean(Template template);

}
