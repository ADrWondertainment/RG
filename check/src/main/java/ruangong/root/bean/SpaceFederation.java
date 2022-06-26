package ruangong.root.bean;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.Cuser;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.bean.dataflow.Astronaut;
import ruangong.root.bean.dataflow.SpacePort;
import ruangong.root.bean.dataflow.SpaceStation;
import ruangong.root.dao.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author pangx
 */
@EqualsAndHashCode(callSuper = true)
@Component
@Scope("singleton")
@Data
public class SpaceFederation extends SpacePort<CuserAstronaut, Approve> {


    @Resource
    private GroupStationMapper groupStationMapper;

    @Resource
    private CuserAstronautMapper cuserAstronautMapper;

    @Resource
    private ApproveMapper approveMapper;


    @Override
    @PostConstruct
    public void init() {
        super.init();
        List<GroupStation> raw = groupStationMapper.selectList(null);
        groupInit(raw);
        List<Approve> approves = approveMapper.selectList(new QueryWrapper<Approve>().eq("pass", 1).isNotNull("flow"));
        List<ApproveField> init = ApproveField.init(this, approves);
        loadAndAssign(init);
    }

    public void groupInit(List<GroupStation> raw) {
        for (GroupStation station : raw) {
            super.registerStation(station, station.getId());
            List<Integer> members = JSONUtil.toList(station.getMember(), Integer.class);
            for (Integer temp : members) {
                CuserAstronaut view = cuserAstronautMapper.selectOne(new QueryWrapper<CuserAstronaut>().eq("id", temp));
                view.setApproveMapper(approveMapper);
                view.setSpaceFederation(this);
                registerAstronaut(view, view.getUid());
                view.setStationBelongsTo(station.getRegisterId());
                station.attend(view);
            }
        }

    }

    public void loadAndAssign(List<ApproveField> init) {
        load(init);
        assign();
    }

    public SpaceStation<CuserAstronaut, Approve> getRegisteredStation(int id) {
        for (SpaceStation<CuserAstronaut, Approve> temp : stations) {
            if (temp.getRegisterId() == id) {
                return temp;
            }
        }
        return null;
    }

    public Astronaut<Approve> getRegisteredAstronaut(int id) {
        for (Astronaut<Approve> temp : astronauts) {
            if (temp.getRegisterId() == id) {
                return temp;
            }
        }
        return null;
    }


}
