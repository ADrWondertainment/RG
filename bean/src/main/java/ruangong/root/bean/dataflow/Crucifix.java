package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Crucifix<LOW> {
    AIMDiffusionField<LOW> oracle(AIMDiffusionField<LOW> high);
}
