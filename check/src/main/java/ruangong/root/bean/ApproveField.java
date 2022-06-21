package ruangong.root.bean;

import cn.hutool.json.JSONUtil;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.SpacePort;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ApproveField extends AIMDiffusionField<CuserAstronaut, Approve> {
    public static List<ApproveField> init(SpacePort<CuserAstronaut, Approve> centralPort, List<Approve> approves) {
        List<ApproveField> approveFields = new LinkedList<>();
        for (Approve approve : approves) {
            ApproveField temp = new ApproveField();
            temp.setContent(approve);
            temp.setCentralPort(centralPort);
            List<Integer> flow = JSONUtil.toList(approve.getFlow(), Integer.class);
            Queue<Integer> integers = new LinkedList<>(flow);
            temp.setSequence(integers);
            approveFields.add(temp);
        }
        return approveFields;
    }
}
