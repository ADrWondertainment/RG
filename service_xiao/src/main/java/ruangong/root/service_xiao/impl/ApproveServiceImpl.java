package ruangong.root.service_xiao.impl;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.Group;
import ruangong.root.bean.GroupDetail;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.dao.GroupDetailMapper;
import ruangong.root.dao.GroupMapper;
import ruangong.root.service_xiao.ApproveService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pangx
 */
@Service
@Transactional
public class ApproveServiceImpl implements ApproveService {

    @Resource
    private GroupDetailMapper groupDetailMapper;

    @Resource
    private GroupMapper groupMapper;

    @Override
    public boolean setGroupWithJson(JSONArray jsonArray, Integer cid) {


        return false;
    }

    @Override
    public JSONArray logGroupStation(JSONArray stations) {

        for (int i = 0; i < stations.size(); i++) {
            JSONObject temp = stations.getJSONObject(i);
            GroupDetail groupDetail = new GroupDetail();
            groupDetail.setMember(temp.getStr("member"));
            groupDetail.setLevel(temp.getInt("level"));

            Group group = new Group();
            group.setCid(temp.getInt("cid"));
            group.setGid(groupDetail.getId());

            if (temp.get("id") == null) {
                groupDetailMapper.insert(groupDetail);
                Integer gid = groupDetail.getId();
                group.setGid(gid);
                groupMapper.insert(group);
            } else {
                Integer id = temp.getInt("id");
                Integer gid = groupMapper.selectById(id).getGid();
                groupDetail.setId(gid);
                groupDetailMapper.updateById(groupDetail);
            }
            stations.set(i, temp.set("id", group.getId()));
        }

        return stations;
    }
}
