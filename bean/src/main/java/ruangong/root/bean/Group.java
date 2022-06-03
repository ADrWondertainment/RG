package ruangong.root.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
@TableName(value = "groups", autoResultMap = true)
public class Group {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    private Integer gid;
}

