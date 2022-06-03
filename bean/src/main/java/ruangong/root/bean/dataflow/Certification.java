package ruangong.root.bean.dataflow;

import lombok.Data;

public interface Certification<LOW> {
    AIMDiffusionField<LOW> atone(Object... args);
    AIMDiffusionField<LOW> process(AIMDiffusionField<LOW> cube);
}
