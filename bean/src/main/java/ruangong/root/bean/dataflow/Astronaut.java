package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Astronaut<LOW> extends Certification<LOW> {
    @TableField(exist = false)
    protected Integer registerId;

    protected void setStation(SpaceStation<Astronaut<LOW>, LOW> spaceStation) {
        super.stationBelongTo = spaceStation;
    }

}
