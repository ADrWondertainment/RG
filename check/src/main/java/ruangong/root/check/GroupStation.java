package ruangong.root.check;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.Answer;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.SpaceStation;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Scope("prototype")
@TableName("group_view")
public class GroupStation extends SpaceStation<CuserAstronaut, Answer> {
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
    public boolean guard(CuserAstronaut cuserView) {
        return true;
    }
}
