package ruangong.root.controller_tao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.Result;
import ruangong.root.bean.User;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
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
    public Result reg(@RequestBody User user){
        result = userService.register(user);
        return result;
    }

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody User user){
        result = userService.login(user);
        if(result.getErrorCode() == ErrorCode.USER_LOGIN_SUCCESS){
            HttpSession session = request.getSession();
            session.setAttribute("email",user.getEmail());
        }
        return result;
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("email");
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "退出成功",
                null
        );
        return result;
    }

    @PostMapping("/changepass")
    public Result changepassword(HttpServletRequest request,@RequestBody JSONObject pass){
        String oldpass =(String) pass.get("oldpass");
        String newpass =(String) pass.get("newpass");
        user.setEmail((String) request.getSession().getAttribute("email"));
        user.setPass(oldpass);
        result = userService.changePassword(user,newpass);
        return result;
    }
}
