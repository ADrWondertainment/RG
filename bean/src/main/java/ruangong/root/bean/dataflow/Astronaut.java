package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author pangx
 */
@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
@Data
public abstract class Astronaut<LOW> implements Certification<LOW> {
    @TableField(exist = false)
    protected Integer registerId;

    @TableField(exist = false)
    protected Integer stationBelongsTo;

    @TableField(exist = false)
    protected SpacePort<? extends Astronaut<LOW>, LOW> centralPort;

}
