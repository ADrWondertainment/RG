package ruangong.root.service_xiao;

import cn.hutool.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ApproveService {

    boolean setGroupWithJson(JSONArray jsonArray, Integer cid);

    JSONArray logGroupStation(JSONArray stations);
}
