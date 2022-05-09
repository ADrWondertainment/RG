package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.Template;

@Mapper
@Repository
public interface TemplateMapper extends BaseMapper<Template> {

    @Select("call test(#{id})")
    public Template getTemplateByIdViaProcedure(@Param("id") int id);

}
