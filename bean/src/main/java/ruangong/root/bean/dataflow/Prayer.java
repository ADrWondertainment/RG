package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Prayer<MEMBER extends Astronaut<LOW>, LOW> {
    /**
     * 在数据处理员将storage里的数据处理后，即将发射前，对数据进行处理以满足发射要求
     * @param received 数据处理员处理后的数据
     * @return 返回满足发射要求的数据
     */
    AIMDiffusionField<MEMBER, LOW> pray(SpaceStation<MEMBER, LOW>.CombinedField received);
}
