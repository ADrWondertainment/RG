package ruangong.root.starter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.bean.User;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.service_xiao.impl.TemplateServiceImpl;

import java.util.Date;

@SpringBootTest
class StarterApplicationTests {

//    @Autowired
//    private TemplateService templateService;

//    @Autowired
//    private Template template;
    @Autowired
    private UserService userService;
    @Autowired
    private Result result;
    @Test
    public void register() {
        User user =new User();
        user.setEmail("uiqtuf");
        user.setPass("123");
        result =userService.register(user);
        System.out.println(result);
    }


}
