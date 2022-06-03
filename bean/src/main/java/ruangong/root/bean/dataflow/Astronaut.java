package ruangong.root.bean.dataflow;

import lombok.Data;

@Data
public abstract class Astronaut<LOW> implements Certification<LOW> {
    private Integer registerId;
}
