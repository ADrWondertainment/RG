package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@TableName("alldept")
@Component
@Scope("prototype")
public class dept {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    private String name;
}
