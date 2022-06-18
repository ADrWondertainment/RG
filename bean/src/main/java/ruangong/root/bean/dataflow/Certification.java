package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Queue;

public abstract interface Certification<LOW> {

    void log(LOW data);

    void atone(Object... args);

    void process(LOW data);

    void getWork();
}
