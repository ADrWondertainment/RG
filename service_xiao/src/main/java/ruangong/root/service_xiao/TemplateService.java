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
     * 通过与数据库直接交互的Template数据与处理前端数据的JsonBeanTemplate格式在templates表中添加或者更新模板
     * @param template 与数据库直接交互的Template数据
     * @param jsonBeanTemplate 处理前端数据的临时数据格式JsonBeanTemplate
     * @return Result字段中data为true表示操作成功
     */
    public Result createOrUpdateTemplateByBean(Template template, JsonBeanTemplate jsonBeanTemplate);

    /**
     * 通过template的id在数据库中删除对应模板
     * @param id template的id
     * @return ErrorCode为66666时表示操作成功
     */
    public Result deleteTemplateById(Integer id);

    /**
     * 通过用户id获得该用户所有template信息并以分页形式返回
     * @param id 用户id
     * @param pageNum 查询分页数据的页码
     * @param size 一页多少条数据
     * @return 在data字段中将分页结果以List形式保存并返回
     */
    public Result getTemplatesInPages(Integer id, Integer pageNum, Integer size);

    /**
     * 通过template的id获取特定的template
     * @param id template的id
     * @return 在data字段中保存获得的Template数据
     */
    public Result getTemplateById(Integer id);

    /**
     * 通过前端传回的数据修改数据库中template的内容
     * @param template 修改后的Template数据
     * @return 是否修改成功
     */
    public boolean modifyTemplate(Template template);


}
