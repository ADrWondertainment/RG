package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.CuserView;

/**
 * @author pangx
 */
@Mapper
@Repository
public interface CuserViewMapper extends BaseMapper<CuserView> {
}
