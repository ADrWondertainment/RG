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
        String code = JSONUtil.parseObj(invite).get("invite", String.class);
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        result = userService.JoinCompany(code, id);
        if (result.getErrorCode().equals(ErrorCode.JOIN_COMPANY_SUCCESS)) {
            UserData userData = userService.GetAllData(id);
            HttpSessionUtil.quickUpdateAttribute(session, userData);
        }
        return result;
    }

    @PostMapping("/rcuser")
    public Result removecuser(@RequestBody String id) {
        String uid = JSONUtil.parseObj(id).get("id", String.class);
        return userService.RemoveCompanyUser(Integer.parseInt(uid));
    }

    @PostMapping("/showdept")
    public Result showdept(HttpServletRequest request, @RequestBody String did) {
        String fid = JSONUtil.parseObj(did).get("fid", String.class);
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        return userService.GetCompanyUserList(cid, Integer.parseInt(fid));
    }

    @PostMapping("/showuserbydept")
    public Result showuserbydept(HttpServletRequest request, @RequestBody String did) {
        String fid = JSONUtil.parseObj(did).get("did", String.class);
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        Result result = userService.ShowCuser(cid, Integer.parseInt(fid));
        return result;
    }

    @PostMapping("/showbylevel")
    public Result showbylevel(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        return userService.GetCuserByLevel(cid);
    }


    @PostMapping("/role")
    public Result updaterole(HttpServletRequest request, @RequestBody String data) {
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        Integer uid = JSONUtil.parseObj(data).get("uid", Integer.TYPE);
        String role = JSONUtil.parseObj(data).get("role", String.class);
        result = userService.UpdateRole(uid, cid, role);
        return result;
    }

    @PostMapping("/sdept")
    public Result setdeptbyuser(HttpServletRequest request, @RequestBody String data) {
        HttpSession session = request.getSession();
        Integer cid = (Integer) session.getAttribute("cid");
        JSONObject entries = JSONUtil.parseObj(data);
        String name = entries.get("name", String.class);
        Integer uid = entries.get("uid", Integer.TYPE);
        return userService.SetDept(uid, name, cid);
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
    public Result updatedept(@RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        Integer did = entries.get("did", Integer.TYPE);
        String department = entries.get("dept", String.class);
        return userService.UpdateDept(did, department);
    }

    @PostMapping("/ddept")
    public Result deletedept(@RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        Integer did = entries.get("did", Integer.TYPE);
        Integer fid = entries.get("fid", Integer.TYPE);
        return userService.RemoveDept(did, fid);
    }

    @PostMapping("/ulevel")
    public Result updatelevel(@RequestBody String data) {
        JSONObject entries = JSONUtil.parseObj(data);
        Integer level = entries.get("level", Integer.TYPE);
        Integer uid = entries.get("uid", Integer.TYPE);
        result = userService.UpdateLevel(uid, level);
        return result;
    }


}
