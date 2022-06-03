package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Prayer<LOW> {
    AIMDiffusionField pray(LOW received, Integer target);


}
