package ruangong.root.bean.dataflow;

/**
 * @author pangx
 */
public interface Prayer<LOW> {
    AIMDiffusionField<LOW> pray(AIMDiffusionField<LOW> received);


}
