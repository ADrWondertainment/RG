package ruangong.root.bean.dataflow;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Queue;

/**
 * @author pangx
 */
public abstract interface Certification<LOW> {

    /**
     * 实现这个方法的目的是对LOW型数据处理后的后台记录
     * @param data 用泛型LOW表示处理的数据
     */
    void log(LOW data);

    /**
     * 实现这个方法的目的是数据处理人需求在Station内主动提出某些请求
     * @param args 表示处理人在Station内主动提出的请求，以列表形式表示
     */
    void atone(Object... args);

    /**
     * 本接口的核心方法之一，实现这个方法就是实现处理人处理数据的方式
     * @param data 处理人要处理的数据
     */
    void process(LOW data);

    /**
     * 处理人获取/更新工作列表
     */
    void getWork();
}
