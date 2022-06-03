package ruangong.root.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;

import java.util.Queue;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Scope("prototype")
public class ApprovalDataStream extends AIMDiffusionField<Answer> {
    public ApprovalDataStream(Answer answer, Queue<Integer> sequence) {
        super.setContent(answer);
        super.setSequence(sequence);
    }
}
