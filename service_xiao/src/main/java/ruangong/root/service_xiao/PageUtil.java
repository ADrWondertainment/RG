package ruangong.root.service_xiao;

import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Component;
import ruangong.root.bean.Result;
import ruangong.root.bean.User;
import ruangong.root.service_tao.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public class PageUtil {


    /*
        {
            "pageNum":1,
            "size":5
        }

     */

    public static HashMap<String, Integer> getPageInfo(JSONObject jsonObject, HttpServletRequest httpServletRequest, UserService userService){

        String email = (String) httpServletRequest.getSession().getAttribute("email");
        Result result = userService.GetUserByEmail(email);
        User user = (User) result.getData();

        Integer id = user.getId();
        Integer pageNum = (Integer) jsonObject.get("pageNum");
        Integer size = (Integer) jsonObject.get("size");

        HashMap<String, Integer> map = new HashMap<>();
        map.put("id", id);
        map.put("pageIndex",pageNum);
        map.put("sizePerPage", size);

        return map;
    }

}
