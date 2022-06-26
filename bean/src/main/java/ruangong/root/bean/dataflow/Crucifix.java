package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Crucifix<MEMBER extends Astronaut<LOW>, LOW> {
    /**
     * 在Station接收到上一个Station发射的数据后对接收到的数据进行初加工
     * @param high 接收到的原始数据，MEMBER表示数据处理人的类别，LOW表示数据类型
     * @return 返回包装在内的需要的数据
     */
    LOW oracle(AIMDiffusionField<MEMBER, LOW> high);
}
