package ruangong.root.bean.dataflow;

public interface Sentinel<MEMBER extends Certification> {
    boolean guard(MEMBER member);
}
