package ruangong.root.bean.dataflow;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Queue;

/**
 * @author pangx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class SpaceStation<MEMBER extends Certification<LOW>, LOW> extends Cathedral implements Sentinel<MEMBER> {

    private Integer id;
    private List<MEMBER> dormitory;
    private Queue<LOW> storage;
    private Queue<LOW> semi;


    private void work(MEMBER member) {

        if (!guard(member) || storage.peek() == null) {
            return;
        }
        semi.offer(member.process(storage.element()));

    }

    private void atone(MEMBER member) {
        if (!guard(member)) {
            return;
        }
        semi.offer(member.atone());
    }

}
