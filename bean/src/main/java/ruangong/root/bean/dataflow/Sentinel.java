package ruangong.root.bean.dataflow;

/**
 * @author pangx
 * @param <MEMBER> 数据处理员的类别
 */
public interface Sentinel<MEMBER extends Certification> {
    /**
     * 在Station与Storage之间校验数据处理员是否被允许进入Storage中处理数据
     * @param member 被验证的MEMBER
     * @return 是否通过验证
     */
    boolean guard(MEMBER member);
}
