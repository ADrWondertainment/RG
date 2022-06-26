package ruangong.root.service_xiao;

import cn.hutool.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pangx
 */
@Service
@Transactional
public interface ApproveService {

    /**
     * 以json形式数据设置group
     * @param jsonArray 接收前端传回的设置数据
     * @param cid 获取公司id
     * @return 是否设置成功
     */
    boolean setGroupWithJson(JSONArray jsonArray, Integer cid);

    /**
     * 更新group信息，在后端以GroupStation形式表达
     * @param stations 更新group信息，在后端以GroupStation形式表达
     * @return 更新后的group信息，以JSONArray形式传回
     */
    JSONArray logGroupStation(JSONArray stations);
}
