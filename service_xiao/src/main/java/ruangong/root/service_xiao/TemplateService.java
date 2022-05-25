package ruangong.root.service_xiao;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import ruangong.root.bean.JsonBeanTemplate;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;

/**
 * @author pangx
 */
@Service
public interface TemplateService extends IService<Template> {

    /**
     *
     * @param template
     * @param jsonBeanTemplate
     * @return
     */
    public Result createOrUpdateTemplateByBean(Template template, JsonBeanTemplate jsonBeanTemplate);

    /**
     *
     * @param id
     * @return
     */
    public Result deleteTemplateById(Integer id);

    /**
     *
     * @param id
     * @param pageNum
     * @param size
     * @return
     */
    public Result getTemplatesInPages(Integer id, Integer pageNum, Integer size);

    /**
     *
     * @param id
     * @return
     */
    public Result getTemplateById(Integer id);


}
