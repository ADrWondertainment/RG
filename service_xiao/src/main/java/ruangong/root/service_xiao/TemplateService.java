package ruangong.root.service_xiao;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;

@Service
public interface TemplateService extends IService<Template> {

    public Result createOrUpdateTemplateByBean(Template template);

    public Result deleteTemplateById(Integer id);

    public Result getTemplatesInPages(Integer id, Integer pageNum, Integer size);


}
