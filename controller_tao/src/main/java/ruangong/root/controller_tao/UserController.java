package ruangong.root.controller_tao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.*;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.utils.HttpSessionUtil;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tao
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private Result result;
    @Resource
    private User user;
    @Resource
    private Company company;

    @PostMapping("/register")
    public Result reg(@RequestBody String data) {

        JSONObject parseObj = JSONUtil.parseObj(data);
        user.setEmail((String) parseObj.get("email"));
        user.setPass((String) parseObj.get("pass"));

        System.out.println(user);

        result = userService.register(user);
        return result;
    }

    /**
     * @return 将userData的数据全部返回前端，UserData字段如下：
     * cid: companyId
     * did: departmentId
     * rid: roleId
     * email: 用户名
     * typeId: 外键连接到存储企业信息的表，若为null则说明不是企业用户
     * company: 企业名称
     * department: 部门名称
     * id: 用户id
     * level: 权限等级（陶沙的设想具体是什么我给忘了）
     * role: 职位名称
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody String user) {
        User user1 = JSONUtil.toBean(user, User.class);
        result = userService.login(user1);
        HttpSession session = null;
        UserData userData = null;
        if (result.getErrorCode().equals(ErrorCode.USER_LOGIN_SUCCESS)) {
            session = request.getSession();
            userData = userService.GetAllData(user1.getEmail());
            System.out.println(userData);
            HttpSessionUtil.quickSetAttribute(session, userData);
        }
        System.out.println(userData);
        result.setData(JSONUtil.toJsonPrettyStr(userData));
        return result;
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        HttpSessionUtil.quickRemoveAttribute(session);
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "退出成功",
                null
        );
        return result;
    }

    @PostMapping("/changepass")
    public Result changepassword(HttpServletRequest request, @RequestBody String data) {

        JSONObject pass = JSONUtil.parseObj(data);
        String oldpass = (String) pass.get("oldpass");
        String newpass = (String) pass.get("newpass");
        user.setEmail((String) request.getSession().getAttribute("email"));
        user.setPass(oldpass);
        result = userService.changePassword(user, newpass);
        return result;
    }

    @PostMapping("/companyregister")
    public Result companyregister(@RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        company = JSONUtil.toBean(entries, Company.class);
        return userService.CompanyRegister(company);
    }

    @PostMapping("/joincompany")
    public Result joincompany(HttpServletRequest request, @RequestBody String invite) {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        result = userService.JoinCompany(invite, id);
        if (result.getErrorCode().equals(ErrorCode.JOIN_COMPANY_SUCCESS)) {
            UserData userData = userService.GetAllData(id);
            HttpSessionUtil.quickUpdateAttribute(session, userData);
        }
        return result;
    }

    @PutMapping("/rcuser")
    public Result removecuser(Integer id) {
        return userService.RemoveCompanyUser(id);
    }

    @PostMapping("/showdept")
    public Result showdept(HttpServletRequest request, @RequestBody String did) {
        String fid = JSONUtil.parseObj(did).get("fid", String.class);
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        return userService.GetCompanyUserList(cid, Integer.parseInt(fid));
    }

    @PostMapping("/showuserbydept")
    public Result showuserbydept(HttpServletRequest request, @RequestBody Integer did) {
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        return userService.GetComanyUserByDepartment(cid, did);
    }

    @PutMapping("/role")
    public Result updaterole(HttpServletRequest request, String role, Integer uid) {
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        result = userService.UpdateRole(uid, cid, role);
        return result;
    }

    @PostMapping("/sdept")
    public Result setdeptbyuser(Integer uid, Integer did) {
        return userService.SetDept(uid, did);
    }

    @PutMapping("/cdept")
    public Result createdept(HttpServletRequest request, @RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        String name = entries.get("name", String.class);
        Integer fid = entries.get("fid", Integer.TYPE);
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        result = userService.CreateDept(cid, name, fid);
        return result;
    }

    @PostMapping("/udept")
    public Result updatedept(Integer did, String department) {
        return userService.UpdateDept(did, department);
    }

    @PostMapping("/ddept")
    public Result deletedept(Integer did, Integer fid) {
        return userService.RemoveDept(did, fid);
    }

    @PutMapping
    public Result updatelevel(Integer level, Integer uid) {
        result = userService.UpdateLevel(uid, level);
        return result;
    }


}
