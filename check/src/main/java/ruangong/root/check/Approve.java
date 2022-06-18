package ruangong.root.check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.SpacePort;

import java.util.*;

@Data
@Component
@Scope("prototype")
@TableName("answers")
public class Approve{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer sid;
    private Integer uid;
    private String data;
    private Integer done;
    private Integer pass;
    private String flow;
}
