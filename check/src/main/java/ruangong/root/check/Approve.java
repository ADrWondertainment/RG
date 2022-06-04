package ruangong.root.check;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.AIMDiffusionField;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Scope("prototype")
@TableName("answer_view")
public class Approve extends AIMDiffusionField<Approve> {
    private Integer id;
    private Integer sid;
    private Integer uid;
    private String data;
    private Integer done;
    private Integer pass;
    private String order;

    public static void init(List<Approve> approveList) {
        for (Approve approve : approveList) {
            approve.setContent(approve);
            char[] chars = approve.getOrder().toCharArray();
            Queue<Integer> integers = new LinkedList<>();
            for (char c : chars) {
                integers.offer(c - '0');
            }
            approve.setSequence(integers);
        }
    }

}
