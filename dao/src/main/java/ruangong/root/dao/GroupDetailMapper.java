package ruangong.root.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.GroupDetail;

/**
 * @author pangx
 */
@Mapper
@Repository
public interface GroupDetailMapper extends BaseMapper<GroupDetail> {
}
