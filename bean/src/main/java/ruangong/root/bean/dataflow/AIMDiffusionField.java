package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author pangx
 */
@SuppressWarnings({"AlibabaAbstractClassShouldStartWithAbstractNaming", "AlibabaClassNamingShouldBeCamel"})
@Data
public abstract class AIMDiffusionField<MEMBER extends Astronaut<LOW>, LOW> {
    @TableField(exist = false)
    private LOW content = null;

    @TableField(exist = false)
    private Queue<Integer> sequence = new LinkedList<>();

    @TableField(exist = false)
    private StatusCode status = StatusCode.POWERLESS;

    @TableField(exist = false)
    private SpacePort<MEMBER, LOW> centralPort;

    protected boolean shoot() {
        Integer target = sequence.peek();
        if (target == null) {
            this.setStatus(StatusCode.FINISHED);
            return false;
        }
        sequence.remove();
        if (this.getStatus() == StatusCode.DEPRECATED) {
            return false;
        }
        if (!centralPort.checkSpaceStation(target)) {
            this.setStatus(StatusCode.DISORIENTED);
            return false;
        }
        this.setStatus(StatusCode.ENERGETIC);
        int index = centralPort.registeredSpaceStations.get(target);
        centralPort.stations.get(index).receive(this);
        return true;
    }

    public static enum StatusCode {
        //标识web项目启动时从数据库中提取出的待审批表或存储于group中待审的审批表
        POWERLESS,
        //标识数据即将被转送
        ENERGETIC,
        //标识数据找不到下一个转送目标点
        DISORIENTED,
        //标识数据损毁
        DAMAGED,
        //标识数据完成所有流程
        FINISHED,
        DEPRECATED
    }

}
