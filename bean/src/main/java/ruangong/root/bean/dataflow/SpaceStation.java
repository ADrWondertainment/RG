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
public abstract class SpaceStation<MEMBER extends Astronaut<LOW>, LOW> extends Cathedral<LOW> implements Sentinel<MEMBER> {

    private Integer registerId;
    private List<MEMBER> dormitory;
    private Queue<AIMDiffusionField<LOW>> storage;
    private Queue<AIMDiffusionField<LOW>> semi;

    public boolean attend(MEMBER member) {
        if (!SpacePort.checkAstronauts(member.getRegisterId())) {
            return false;
        }
        dormitory.add(member);
        return true;
    }

    public boolean leave(MEMBER member) {
        return dormitory.remove(member);
    }

    protected void receive(AIMDiffusionField<LOW> field) {
        switch (field.getStatus()) {
            case FINISHED:
            case POWERLESS:
            case DAMAGED:
            case DISORIENTED:
                restore(field);
            case ENERGETIC:
                field.setStatus(AIMDiffusionField.StatusCode.POWERLESS);
        }
        storage.offer(oracle(field));
    }

    private void restore(AIMDiffusionField<LOW> field) {
        if (!recycle(field)) {
            deprecate(field);
        }
    }

    private boolean recycle(AIMDiffusionField<LOW> field) {
        switch (field.getStatus()) {
            case DISORIENTED:
                SpacePort.disoriented.offer(field);
                return true;
            case DAMAGED:
                SpacePort.damaged.offer(field);
                return true;
            case POWERLESS:
                SpacePort.powerless.offer(field);
                return true;
            case FINISHED:
                SpacePort.finished.offer(field);
                return true;
            default:
                return false;
        }
    }

    private void deprecate(AIMDiffusionField<LOW> field) {

    }

    private boolean transmit() {
        if (semi.peek() == null) {
            return false;
        }
        AIMDiffusionField<LOW> prayed = pray(semi.element());
        if (prayed != null) {
            if (!prayed.shoot()) {
                restore(prayed);
                return false;
            }
            semi.remove();
            return true;
        }
        semi.offer(semi.remove());
        return false;
    }

    private boolean work(MEMBER member) {
        if (!guard(member) || storage.peek() == null) {
            return false;
        }
        if (semi.offer(member.process(storage.element()))) {
            storage.remove();
            return true;
        }
        storage.offer(storage.remove());
        return false;
    }

    private boolean atone(MEMBER member) {
        if (!guard(member)) {
            return false;
        }
        return semi.offer(member.atone());
    }
}
