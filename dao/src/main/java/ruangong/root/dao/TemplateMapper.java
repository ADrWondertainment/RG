package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.Template;

@Mapper
@Repository
public interface TemplateMapper extends BaseMapper<Template> {
}
