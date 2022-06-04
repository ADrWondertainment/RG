package ruangong.root.check;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.Answer;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.Astronaut;

/**
 * @author pangx
 */
@EqualsAndHashCode(callSuper = true)
@Component
@Scope("prototype")
@Data
@TableName("cuser_view")
public class CuserAstronaut extends Astronaut<Answer> {

    private Integer id;
    private Integer uid;
    private Integer groupId;
    private String email;
    private String role;
    private String dept;

    @Override
    public AIMDiffusionField<Answer> atone(Object... args) {
        return null;
    }

    @Override
    public AIMDiffusionField<Answer> process(AIMDiffusionField<Answer> cube) {
        return null;
    }
}
