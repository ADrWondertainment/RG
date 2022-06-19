package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.Astronaut;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
@TableName("cuser_view")
public class CuserView {

    private Integer id;
    private Integer uid;
    private Integer cid;
    private Integer level;
    private Integer groupId;
    private String email;
    private String role;
    private String dept;

}
