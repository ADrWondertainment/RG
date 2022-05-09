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

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",username);
        User user_result = userMapper.selectOne(wrapper);

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
}