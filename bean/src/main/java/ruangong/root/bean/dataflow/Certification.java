package ruangong.root.bean.dataflow;

public interface Certification<LOW> {
    LOW atone(Object... args);
    LOW process(LOW cube);
}
