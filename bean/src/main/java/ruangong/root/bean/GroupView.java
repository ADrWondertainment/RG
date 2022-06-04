package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.SpaceStation;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Scope("prototype")
@TableName("group_view")
public class GroupView extends SpaceStation<CuserView, Answer> {
    private Integer id;
    private Integer cid;
    private Integer level;
    private String member;

    @Override
    public AIMDiffusionField<Answer> oracle(AIMDiffusionField<Answer> high) {
        return null;
    }

    @Override
    public AIMDiffusionField<Answer> pray(AIMDiffusionField<Answer> received) {
        return null;
    }


    @Override
    public boolean guard(CuserView cuserView) {
        return true;
    }
}
