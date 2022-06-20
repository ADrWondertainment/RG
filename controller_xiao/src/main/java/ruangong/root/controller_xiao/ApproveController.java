package ruangong.root.controller_xiao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.CuserView;
import ruangong.root.bean.GroupView;
import ruangong.root.bean.Result;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.check.Approve;
import ruangong.root.check.ApproveField;
import ruangong.root.check.CuserAstronaut;
import ruangong.root.check.SpaceFederation;
import ruangong.root.dao.CuserViewMapper;
import ruangong.root.dao.GroupStationMapper;
import ruangong.root.dao.GroupViewMapper;
import ruangong.root.exception.ErrorCode;
import ruangong.root.utils.HttpSessionUtil;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/approves")
public class ApproveController {

    @Resource
    private Result result;

    @Resource
    private SpaceFederation spaceFederation;

    @Resource
    private GroupViewMapper groupViewMapper;

    @Resource
    private CuserViewMapper cuserViewMapper;

    @PostMapping("/show")
    public Result showSubmittedApproves(HttpServletRequest httpServletRequest) {
        Integer id = Integer.parseInt((String) httpServletRequest.getSession().getAttribute("id"));
        Queue<AIMDiffusionField<CuserAstronaut, Approve>> finished = spaceFederation.getFinished();

        Queue<ApproveField> allApproves = new LinkedList<>();
        for (AIMDiffusionField<CuserAstronaut, Approve> temp : finished) {
            allApproves.offer((ApproveField) temp);
        }
        Queue<Approve> myApproves = new LinkedList<>();
        for (ApproveField temp : allApproves) {
            Approve content = temp.getContent();
            if (content.getUid().equals(id)) {
                myApproves.offer(temp.getContent());
            }
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "找到用户所有提交审批表",
                JSONUtil.parseArray(myApproves)
        );


        return result;
    }

    @PostMapping("/groups")
    public Result getAllGroups(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        List<GroupView> groups = groupViewMapper.selectList(new QueryWrapper<GroupView>().eq("cid", cid));

        JSONArray groupArray = JSONUtil.parseArray(groups);
        for (int i = 0; i < groups.size(); i++) {
            JSONObject temp = (JSONObject) groupArray.get(i);
            String member = temp.get("member", String.class);
            List<Integer> integers = JSONUtil.toList(member, Integer.TYPE);
            JSONArray objects = new JSONArray();
            for (Integer integer : integers) {
                CuserView id = cuserViewMapper.selectOne(new QueryWrapper<CuserView>().eq("id", integer));
                objects.add(JSONUtil.createObj().putOnce("id", integer).putOnce("email", id.getEmail()));
            }
            temp.set("member", objects);
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                groupArray
        );

        return result;
    }

    @PostMapping("/cusers")
    public Result getAllCompanyUsers(HttpServletRequest httpServletRequest) {
        int cid = Integer.parseInt((String) (httpServletRequest.getSession().getAttribute("cid")));

        List<CuserView> cuserViews = cuserViewMapper.selectList(new QueryWrapper<CuserView>().eq("cid", cid));

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "okay",
                cuserViews
        );


        return result;
    }

    @PostMapping("/set")
    public Result setGroup(@RequestBody String data){

        return null;
    }

}
