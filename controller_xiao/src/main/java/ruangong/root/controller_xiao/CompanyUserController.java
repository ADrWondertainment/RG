package ruangong.root.controller_xiao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Cuser;
import ruangong.root.bean.CuserView;
import ruangong.root.bean.Result;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_xiao.CompanyUserService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pangx
 */
@RestController
@RequestMapping("/cusers")
public class CompanyUserController {

    @Resource
    private Result result;

    @Resource
    private CompanyUserService companyUserService;

    @GetMapping("/{id}")
    public Result getAllCompanyUsers(@PathVariable String id) {

        List<CuserView> cuserView = companyUserService.getCuserView();

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "所有审批用户信息返回成功",
                cuserView
        );

        return result;
    }

}
