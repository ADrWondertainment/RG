package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.Template;

/**
 * @author pangx
 */
@Mapper
@Repository
public interface TemplateMapper extends BaseMapper<Template> {
    /**
     * 利用存储过程获得Template，但没有实装
     * @param id test的id
     * @return Template对象
     */
    @Select("call test(#{id})")
    public Template getTemplateByIdViaProcedure(@Param("id") int id);

}
