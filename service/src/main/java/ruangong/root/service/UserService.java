package ruangong.root.service;


import org.springframework.stereotype.Service;
import ruangong.root.bean.User;


public interface UserService {

    public User getUserById(int id);
}
