package ruangong.root.bean.dataflow;

import lombok.Data;

import java.util.Queue;

/**
 * @author pangx
 */
@Data
public abstract class AIMDiffusionField<LOW> {
    private LOW content;
    private Queue<Integer> sequence;
    private StatusCode status;

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
        POWERLESS, ENERGETIC, DISORIENTED, DAMAGED, FINISHED
    }

}
