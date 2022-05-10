package ruangong.root.service_tao;

import com.baomidou.mybatisplus.extension.service.IService;
import ruangong.root.bean.Result;
import ruangong.root.bean.User;

public interface UserService extends IService<User> {
    public Result register(User user);

    public Result login(User user);

    public Result GetUserByEmail(String email);

    public Result changePassword(User user,String newpass);
}
