package ruangong.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.User;
import ruangong.root.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @GetMapping
    public void test(){

        User byId = userService.getUserById(1);
        System.out.println(byId);
    }

}
