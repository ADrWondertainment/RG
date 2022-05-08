package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ruangong.root.bean.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
