package ruangong.root.controller_xiao;

import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Result;
import ruangong.root.bean.dataflow.AIMDiffusionField;
import ruangong.root.check.Approve;
import ruangong.root.check.ApproveField;
import ruangong.root.check.CuserAstronaut;
import ruangong.root.check.SpaceFederation;
import ruangong.root.exception.ErrorCode;
import ruangong.root.utils.HttpSessionUtil;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Queue;

@RestController("/approves")
public class ApproveController {

    @Resource
    private Result result;

    @Resource
    private SpaceFederation spaceFederation;

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
}
