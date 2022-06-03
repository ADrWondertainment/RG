package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Prayer<LOW> {
    AIMDiffusionField<LOW> pray(LOW received, Integer target);


}
