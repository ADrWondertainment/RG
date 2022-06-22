package ruangong.root.service_xiao;

import cn.hutool.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApproveService {

    boolean setGroupWithJson(JSONArray jsonArray, Integer cid);

    boolean logGroupStation(JSONArray stations);
}
