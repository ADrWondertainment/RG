package ruangong.root.check;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.Astronaut;
import ruangong.root.bean.dataflow.SpaceStation;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author pangx
 */
@EqualsAndHashCode(callSuper = true)
@Component
@Scope("prototype")
@Data
@TableName("cuser_view")
public class CuserAstronaut extends Astronaut<Approve> {

    private Integer id;
    private Integer uid;
    private Integer groupId;
    private String email;
    private String role;
    private String dept;

    @TableField(exist = false)
    private List<Approve> workList;

    public void setStation(SpaceStation<Astronaut<Approve>, Approve> spaceStation) {
        super.setStation(spaceStation);
    }

    public void getApproves() {
        Queue<SpaceStation<Astronaut<Approve>, Approve>.CombinedField> work = super.getWork();
        workList = new ArrayList<>();
        for (SpaceStation<Astronaut<Approve>, Approve>.CombinedField field : work) {
            workList.add(field.storage);
        }
    }

    public void process(String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        int index = Integer.parseInt((String) entries.get("index"));
        int pass = Integer.parseInt((String) entries.get("pass"));
        Approve selected = workList.get(index);
        if (pass == 2) {
            selected.setPass(2);
        }

        stationBelongTo.transmit(selected);
    }

}
