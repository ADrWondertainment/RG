package ruangong.root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruangong.root.bean.User;
import ruangong.root.dao.UserMapper;
import ruangong.root.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(int id) {
        return (userMapper.selectById(1));
    }
}
