package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.Role;

/**
 * @author tao
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
