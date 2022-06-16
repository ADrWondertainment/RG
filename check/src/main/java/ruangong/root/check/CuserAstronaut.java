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
import ruangong.root.dao.ApproveMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author pangx
 */
//@EqualsAndHashCode(callSuper = true)
//@Data
//一用Lombok就报错，不懂为什么，我猜测是因为继承了抽象类
@TableName(value = "cuser_view")
@Component
@Scope("prototype")
public class CuserAstronaut extends Astronaut<Approve> {

    private Integer id;
    private Integer uid;
    private Integer groupId;
    private String email;
    private String role;
    private String dept;

    @TableField(exist = false)
    @Resource
    private ApproveMapper approveMapper;

    @TableField(exist = false)
    @Resource
    private SpaceFederation spaceFederation;

    @TableField(exist = false)
    private List<Approve> workList;

    @TableField(exist = false)
    private LinkedList<SpaceStation<CuserAstronaut, Approve>.CombinedField> work;

    public void approve(String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        int index = Integer.parseInt((String) entries.get("index"));
        int pass = Integer.parseInt((String) entries.get("pass"));
        Approve selected = workList.get(index);
        if (pass == 0) {
            process(selected);
            log(selected);
        }
        spaceFederation.getRegisteredStation(stationBelongsTo).transmit(selected);
    }

    @Override
    public void log(Approve approve) {
        int index = workList.indexOf(approve);
        SpaceStation<CuserAstronaut, Approve>.CombinedField combinedField = work.get(index);
        ApproveField field = (ApproveField) combinedField.fieldStorage;
        field.setContent(approve);
        Queue<Integer> sequence = field.getSequence();
        approve.setFlow(JSONUtil.toJsonStr(sequence));
        approveMapper.update(approve, null);
    }


    @Override
    public void atone(Object... args) {

    }

    @Override
    public void process(Approve data) {
        data.setPass(0);
    }

    @Override
    public void getWork() {
        work = (LinkedList<SpaceStation<CuserAstronaut, Approve>.CombinedField>) spaceFederation.getRegisteredStation(stationBelongsTo).getCombinedFields();
        workList = new ArrayList<>();
        for (SpaceStation<CuserAstronaut, Approve>.CombinedField field : work) {
            workList.add(field.storage);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public ApproveMapper getApproveMapper() {
        return approveMapper;
    }

    public void setApproveMapper(ApproveMapper approveMapper) {
        this.approveMapper = approveMapper;
    }

    public SpaceFederation getSpaceFederation() {
        return spaceFederation;
    }

    public void setSpaceFederation(SpaceFederation spaceFederation) {
        this.spaceFederation = spaceFederation;
    }

    public List<Approve> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Approve> workList) {
        this.workList = workList;
    }

    public void setWork(LinkedList<SpaceStation<CuserAstronaut, Approve>.CombinedField> work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
