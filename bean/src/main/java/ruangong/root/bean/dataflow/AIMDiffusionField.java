package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author pangx
 */
@Data
public abstract class AIMDiffusionField<LOW> {
    @TableField(exist = false)
    private LOW content = null;

    @TableField(exist = false)
    private Queue<Integer> sequence = new LinkedList<>();

    @TableField(exist = false)
    private StatusCode status = StatusCode.POWERLESS;

    protected boolean shoot() {
        Integer target = sequence.peek();
        if (target == null) {
            this.setStatus(StatusCode.FINISHED);
            return false;
        }
        if (!SpacePort.checkSpaceStation(target)) {
            this.setStatus(StatusCode.DISORIENTED);
            return false;
        }
        this.setStatus(StatusCode.ENERGETIC);
        SpacePort.stations.get(target).receive(this);
        return true;
    }

    protected static enum StatusCode {
        POWERLESS, ENERGETIC, DISORIENTED, DAMAGED, FINISHED, DEPRECATED
    }

}
