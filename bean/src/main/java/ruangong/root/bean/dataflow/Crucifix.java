package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Crucifix<LOW> {
    public LOW oracle(AIMDiffusionField<LOW> high);
}
