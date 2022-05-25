package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author pangx
 */
@Data
@TableName("allrole")
@Component
@Scope("prototype")
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    private String name;
}
