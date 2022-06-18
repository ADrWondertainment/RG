package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Crucifix<MEMBER extends Astronaut<LOW>, LOW> {
    LOW oracle(AIMDiffusionField<MEMBER, LOW> high);
}
