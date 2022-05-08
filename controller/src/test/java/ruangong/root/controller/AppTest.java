package ruangong.root.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruangong.root.bean.User;
import ruangong.root.dao.UserMapper;

@SpringBootTest
class AppTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

}
