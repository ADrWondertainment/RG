package ruangong.root.bean.dataflow;

import lombok.Data;

/**
 * @author pangx
 */
@Data
public abstract class AIMDiffusionField<SKILL> {
    private SKILL content;

    protected AIMDiffusionField<SKILL> aimDiffusionFieldFactory() {
        return new AIMDiffusionField<SKILL>() {
        };
    }

    protected AIMDiffusionField aimDiffusionFieldFactory(SKILL content) {
        AIMDiffusionField field = new AIMDiffusionField<SKILL>() {
        };
        field.setContent(content);
        return field;
    }
}
