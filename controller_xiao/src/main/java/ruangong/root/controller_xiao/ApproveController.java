package ruangong.root.controller_xiao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONNull;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.*;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.dao.CuserViewMapper;
import ruangong.root.dao.GroupViewMapper;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_xiao.ApproveService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author pangx
 */
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

    @Resource
    private ApproveService approveService;

    @PostMapping("/show")
    public Result showSubmittedApproves(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer id = (Integer) session.getAttribute("id");
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

    /**
     * [
     * {
     * label: "业务部",
     * id: 1,
     * members: [{ id: 1, email: "张三" }],
     * },
     * {
     * label: "市场分析",
     * id: 2,
     * members: [],
     * },
     * {
     * label: "信息搜集",
     * id: 3,
     * members: [],
     * },
     * ]
     */
    @PostMapping("/set")
    public Result setGroup(@RequestBody String data, HttpServletRequest httpServletRequest) {
        Integer cid = (Integer) httpServletRequest.getSession().getAttribute("cid");
        JSONObject raw = JSONUtil.parseObj(data);
        JSONArray rawArray = raw.getJSONArray("submitData");
        List<GroupStation> arrayList = new ArrayList<>();
        for (int i = 0; i < rawArray.size(); i++) {
            JSONObject jsonObject = rawArray.getJSONObject(i);
            GroupStation groupStation = new GroupStation();
            Object id = jsonObject.get("id");
            if (id != JSONNull.NULL) {
                groupStation.setId((Integer) id);
            }
            groupStation.setCid(cid);
            groupStation.setLevel(1);
            Object member = jsonObject.get("member");
            if (member == null) {
                throw new FrontException(ErrorCode.MEMBER_NULL, "group内成员不能为空");
            } else {
                JSONArray objects = (JSONArray) member;
                if (objects.isEmpty()) {
                    throw new FrontException(ErrorCode.MEMBER_NULL, "group内成员不能为空");
                }
            }
            groupStation.setMember(JSONUtil.toJsonStr(member));
            arrayList.add(groupStation);
        }

        JSONArray array = JSONUtil.createArray();
        for (GroupStation groupStation : arrayList) {
            JSONObject entries = new JSONObject();
            entries.putOnce("id", groupStation.getId()).putOnce("cid", groupStation.getCid()).putOnce("level", groupStation.getLevel()).putOnce("member", groupStation.getMember());
            array.add(entries);
        }
        JSONArray objects = approveService.logGroupStation(array);
        List<GroupStation> temp = JSONUtil.toList(objects, GroupStation.class);
        spaceFederation.groupInit(temp);
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "ok",
                null
        );
        return result;
    }

}
