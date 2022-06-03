package ruangong.root.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.SpaceStation;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author pangx
 */
@EqualsAndHashCode(callSuper = true)
@Component
@Scope("prototype")
@Data
@TableName(value = "groups", autoResultMap = true)
public class Group extends SpaceStation<Cuser, Answer> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer cid;
    private Integer gid;

    @Override
    public AIMDiffusionField<Answer> oracle(AIMDiffusionField<Answer> high) {
        return high;
    }

    @Override
    public AIMDiffusionField<Answer> pray(AIMDiffusionField<Answer> received) {
        return received;
    }

    @Override
    public boolean guard(Cuser cuser) {
        return true;
    }
}

