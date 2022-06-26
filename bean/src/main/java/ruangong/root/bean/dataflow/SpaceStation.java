package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author pangx
 */
@SuppressWarnings({"AlibabaSwitchStatement", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class SpaceStation<MEMBER extends Astronaut<LOW>, LOW> extends Cathedral<MEMBER, LOW> implements Sentinel<MEMBER> {

    public class CombinedField {
        @TableField(exist = false)
        public LOW storage = null;

        @TableField(exist = false)
        public AIMDiffusionField<MEMBER, LOW> fieldStorage = null;

    }

    private CombinedField combinedFieldFactory(AIMDiffusionField<MEMBER, LOW> field) {
        CombinedField temp = new CombinedField();
        temp.fieldStorage = field;
        temp.storage = field.getContent();
        return temp;
    }

    @TableField(exist = false)
    private Integer registerId;

    @TableField(exist = false)
    private List<MEMBER> dormitory = new ArrayList<>();

    @TableField(exist = false)
    private Queue<CombinedField> combinedFields = new LinkedList<>();

    @TableField(exist = false)
    private Queue<LOW> semi = new LinkedList<>();

    @TableField(exist = false)
    private SpacePort<MEMBER, LOW> centralPort;


    public boolean attend(MEMBER member) {
        if (!centralPort.checkAstronauts(member.getRegisterId())) {
            return false;
        }
        dormitory.add(member);
        return true;
    }

    public boolean leave(MEMBER member) {
        return dormitory.remove(member);
    }

    @SuppressWarnings("AlibabaSwitchStatement")
    protected void receive(AIMDiffusionField<MEMBER, LOW> field) {
        //noinspection AlibabaSwitchStatement
        switch (field.getStatus()) {
            case FINISHED:
            case DISORIENTED:
            case POWERLESS:
            case DAMAGED:
                recycle(field);
                return;
            case ENERGETIC:
                field.setStatus(AIMDiffusionField.StatusCode.POWERLESS);
        }
        combinedFields.offer(combinedFieldFactory(field));
    }


    private void recycle(AIMDiffusionField<MEMBER, LOW> field) {
        switch (field.getStatus()) {
            case DISORIENTED:
                centralPort.disoriented.offer(field);
                return;
            case DAMAGED:
                centralPort.damaged.offer(field);
                return;
            case POWERLESS:
                centralPort.powerless.offer(field);
                return;
            case FINISHED:
                centralPort.finished.offer(field);
                return;
            case DEPRECATED:
                centralPort.deprecated.offer(field);
        }
    }


    public boolean transmit(LOW selected) {
        CombinedField temp = null;
        for (CombinedField field : combinedFields) {
            if (field.storage == selected) {
                temp = field;
            }
        }
        if (temp == null) {
            return false;
        }
        AIMDiffusionField<MEMBER, LOW> prayed = pray(temp);
        if (prayed != null) {
            if (!prayed.shoot()) {
                recycle(prayed);
                combinedFields.remove(temp);
                return false;
            }
            combinedFields.remove(temp);
            return true;
        }
        combinedFields.offer(combinedFields.remove());
        return false;
    }

}
