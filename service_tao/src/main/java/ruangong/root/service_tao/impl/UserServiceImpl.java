package ruangong.root.service_tao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruangong.root.bean.Result;
import ruangong.root.bean.User;
import ruangong.root.dao.UserMapper;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.utils.ResultUtil;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Result result;

    @Override
    public Result register(User user) {

        String username = user.getEmail();

        Result temp_result = GetUserByEmail(username);
        User user_result = (User) temp_result.getData();

        //重名出错
        if(user_result != null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_NAME_DUPLICATED,
                    "用户名重复，请重命名",
                    null
            );
            return result;
        }

        //注册功能
        int insert = userMapper.insert(user);

        if(insert !=1){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_INSERT_FAILURE,
                    "注册失败，请稍后再试",
                    null
            );
            return result;
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.USER_INSERT_SUCCESS,
                "注册成功",
                1
        );
        return result;
    }

    @Override
    public Result login(User user) {
        String username = user.getEmail();
        String password = user.getPass();

        Result temp_result = GetUserByEmail(username);
        User user_result = (User) temp_result.getData();

        if(user_result ==null){
            return temp_result;
        }
        String real_pass =user_result.getPass();
        if(!real_pass.equals(password)){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_PASSWORD_UNMATCH,
                    "密码错误，请重新输入",
                    null
            );
            return result;
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.USER_LOGIN_SUCCESS,
                "登录成功",
                1
        );
        return result;
    }

    @Override
    public Result GetUserByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User user_result = userMapper.selectOne(wrapper);
        if(user_result ==null){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_NAME_UNFINDED,
                    "该用户不存在",
                    null
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.SUCCESS,
                "查询成功",
                user_result
        );
        return result;
    }

    @Override
    public Result changePassword(User user, String newpass) {
        String username =user.getEmail();
        String oldpass =user.getPass();

        Result temp_result = GetUserByEmail(username);
        User user_result =(User) temp_result.getData();

        if(user_result == null){
            return temp_result;
        }

        if(!oldpass.equals(user_result.getPass())){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_PASSWORD_UNMATCH,
                    "密码错误，请重新输入",
                    null
            );
            return result;
        }

        user_result.setPass(newpass);
        int update = userMapper.updateById(user_result);

        if(update !=1){
            ResultUtil.quickSet(
                    result,
                    ErrorCode.USER_CHANGEPASS_FAILURE,
                    "修改密码失败，请稍后再试",
                    null
            );
            return result;
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.USER_CHANGEPASS_SUCCESS,
                "修改密码成功",
                1
        );
        return result;
    }
}
