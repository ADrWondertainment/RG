package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Queue;

public abstract class Certification<LOW> {

    @TableField(exist = false)
    protected SpacePort<Astronaut<LOW>, LOW> centralPort;

    @TableField(exist = false)
    protected SpaceStation< Astronaut<LOW>, LOW> stationBelongTo;

    protected void setStation(SpaceStation< Astronaut<LOW>, LOW> spaceStation) {
        this.stationBelongTo = spaceStation;
    }

    protected void atone(Object... args) {
    }

    protected void process(LOW data) {
    }


    protected Queue<SpaceStation<Astronaut<LOW>, LOW>.CombinedField> getWork() {
        return stationBelongTo.getCombinedFields();
    }
}
