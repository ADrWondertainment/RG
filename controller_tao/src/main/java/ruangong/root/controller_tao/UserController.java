package ruangong.root.controller_tao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.Result;
import ruangong.root.bean.User;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.utils.ResultUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Result result;

    @PostMapping("/register")
    public Result reg(@RequestBody User user){
        result = userService.register(user);
        return result;
    }

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody User user){
        result = userService.login(user);
        if(result.getErrorCode() == ErrorCode.USER_LOGIN_SUCCESS){
            request.getSession().setAttribute("email",user.getEmail());
        }
        return result;
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().removeAttribute("email");
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "退出成功",
                null
        );
        return result;
    }
}
