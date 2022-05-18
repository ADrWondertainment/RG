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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Result result;
    @Autowired
    private User user;

    @PostMapping("/register")
    public Result reg(@RequestBody String data) {

        JSONObject parseObj = JSONUtil.parseObj(data);
        user.setEmail((String) parseObj.get("email"));
        user.setPass((String) parseObj.get("pass"));

        System.out.println(user);

        result = userService.register(user);
        return result;
    }

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody User user) {
        result = userService.login(user);
        if (result.getErrorCode() == ErrorCode.USER_LOGIN_SUCCESS) {
            HttpSession session = request.getSession();
            UserData userData = userService.GetAllData(user.getId());
            HttpSessionUtil.quickSetAttribute(session,userData);
        }
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
    public Result changepassword(HttpServletRequest request, @RequestBody JSONObject pass) {
        String oldpass = (String) pass.get("oldpass");
        String newpass = (String) pass.get("newpass");
        user.setEmail((String) request.getSession().getAttribute("email"));
        user.setPass(oldpass);
        result = userService.changePassword(user, newpass);
        return result;
    }

    @PostMapping("/companyregister")
    public Result companyregister(@RequestBody Company company){
        result =userService.CompanyRegister(company);
        return result;
    }

    @PostMapping ("/joincompany")
    public Result joincompany(HttpServletRequest request, @RequestBody String invite){
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        result =userService.JoinCompany(invite,id);
        if(result.getErrorCode().equals(ErrorCode.JOIN_COMPANY_SUCCESS)){
            UserData userData = userService.GetAllData(id);
            HttpSessionUtil.quickUpdateAttribute(session,userData);
        }
        return result;
    }

    @GetMapping
    public List<CompanyUser> showallcuser(HttpServletRequest request){
        HttpSession session=request.getSession();
        Integer cid =(Integer) session.getAttribute("cid");
        return userService.GetAllCompanyUser(cid);
    }

    @PutMapping
    public Result updaterole(HttpServletRequest request,String role){
        HttpSession session = request.getSession();
        Integer cid =(Integer) session.getAttribute("cid");
        Integer id =(Integer) session.getAttribute("id");
        result = userService.UpdateRole(id,cid,role);
        UserData userData = userService.GetAllData(id);
        HttpSessionUtil.quickUpdateAttribute(session,userData);
        return result;
    }

    @PutMapping
    public Result updatedept(HttpServletRequest request,String department){
        HttpSession session = request.getSession();
        Integer cid =(Integer) session.getAttribute("cid");
        Integer id =(Integer) session.getAttribute("id");
        result = userService.UpdateRole(id,cid,department);
        UserData userData = userService.GetAllData(id);
        HttpSessionUtil.quickUpdateAttribute(session,userData);
        return result;
    }

    @PutMapping
    public Result updatelevel(HttpServletRequest request,Integer level){
        HttpSession session = request.getSession();
        Integer id =(Integer) session.getAttribute("id");
        result = userService.UpdateLevel(id,level);
        UserData userData = userService.GetAllData(id);
        HttpSessionUtil.quickUpdateAttribute(session,userData);
        return result;
    }
}
