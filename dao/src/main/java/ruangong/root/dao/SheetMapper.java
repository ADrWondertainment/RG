package ruangong.root.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ruangong.root.bean.Sheet;

/**
 * @author pangx
 */
@Mapper
@Repository
public interface SheetMapper extends BaseMapper<Sheet> {
}
