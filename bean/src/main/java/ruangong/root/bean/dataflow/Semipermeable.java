package ruangong.root.bean.dataflow;

public interface Semipermeable<HIGH, LOW> {
    public LOW filt(HIGH high);
}
