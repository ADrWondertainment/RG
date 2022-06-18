package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Prayer<MEMBER extends Astronaut<LOW>, LOW> {
    AIMDiffusionField<MEMBER, LOW> pray(SpaceStation<MEMBER, LOW>.CombinedField received);
}
