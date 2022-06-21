package ruangong.root.bean;

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
public class GroupStation extends SpaceStation<CuserAstronaut, Approve> {
    private Integer id;
    private Integer cid;
    private Integer level;
    private String member;


    @Override
    public boolean guard(CuserAstronaut cuserAstronaut) {
        return true;
    }


    @Override
    public Approve oracle(AIMDiffusionField<CuserAstronaut, Approve> high) {
        return high.getContent();
    }

    @Override
    public AIMDiffusionField<CuserAstronaut, Approve> pray(SpaceStation<CuserAstronaut, Approve>.CombinedField received) {

        received.fieldStorage.setContent(received.storage);
        if (received.storage.getPass() == 0) {
            received.fieldStorage.setStatus(AIMDiffusionField.StatusCode.DEPRECATED);
        }
        return received.fieldStorage;
    }
}
