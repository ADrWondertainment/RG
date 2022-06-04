package ruangong.root.check;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.dataflow.SpacePort;
import ruangong.root.dao.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Component
@Scope("singleton")
@Data
public class SpaceFederation extends SpacePort {


    @Resource
    private GroupStationMapper groupStationMapper;

    @Resource
    private CuserAstronautMapper cuserAstronautMapper;

    @PostConstruct
    public void init() {

        List<GroupStation> raw = groupStationMapper.selectList(null);
        for (GroupStation station : raw) {

            registerStation(station);

            char[] array = station.getMember().toCharArray();
            for (char c : array) {
                int temp = c - '0';
                CuserAstronaut view = cuserAstronautMapper.selectOne(new QueryWrapper<CuserAstronaut>().eq("id", temp));
                registerAstronaut(view);
                station.attend(view);
            }

        }
    }

}
