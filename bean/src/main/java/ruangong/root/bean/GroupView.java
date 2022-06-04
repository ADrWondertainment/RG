package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.SpaceStation;

@Data
@Component
@Scope("prototype")
@TableName("group_view")
public class GroupView {
    private Integer id;
    private Integer cid;
    private Integer level;
    private String member;

}
