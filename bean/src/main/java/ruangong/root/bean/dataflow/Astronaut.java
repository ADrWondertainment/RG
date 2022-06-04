package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public abstract class Astronaut<LOW> implements Certification<LOW> {
    @TableField(exist = false)
    private Integer registerId;
}
