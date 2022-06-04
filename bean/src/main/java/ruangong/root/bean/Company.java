package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.SpacePort;

/**
 * @author tao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("companies")
@Component
@Scope("prototype")
public class Company extends SpacePort {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String invite;
    private String groups;
}
