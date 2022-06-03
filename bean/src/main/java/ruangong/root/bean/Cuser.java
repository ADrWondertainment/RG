package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("cusers")
@Component
@Scope("prototype")
public class Cuser extends Astronaut<Answer> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer cid;
    private Integer rid;
    private Integer did;
    private Integer level;
    private Integer group;

    @Override
    public AIMDiffusionField<Answer> atone(Object... args) {
        return null;
    }

    @Override
    public AIMDiffusionField<Answer> process(AIMDiffusionField<Answer> cube) {



        return null;
    }
}
