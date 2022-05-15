package ruangong.root.controller_xiao;


import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;
import ruangong.root.bean.UrlResourcedLocation;
import ruangong.root.bean.User;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.service_xiao.PageUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    @Resource
    private SheetService sheetService;
    @Resource
    private UserService userService;

    /*
    {
        "tid":1,
        "uid":2,
        "name":"test",
        "description":"sample sheet",
        "start":"2022-05-15",
        "end":"2022-05-18",
        "type":1,
        "length":4
    }
    */
    @PostMapping
    public Result debutSheet(@RequestBody Sheet sheet, HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getSession().getAttribute("email");
        if (email == null) {
            throw new BackException(ErrorCode.USER_ILLEGAL_ACCESS, "用户未登录");
        }
        Result result = userService.GetUserByEmail(email);
        User user = (User) result.getData();

        return sheetService.fastCreateSheet(sheet, user.getId());
    }


    @PostMapping("/url")
    public Result enableSheetUrl(@RequestBody UrlResourcedLocation urlResourcedLocation) {
        return sheetService.updateLocatedUrl(urlResourcedLocation);

    }

    @GetMapping
    public Result getSheetsInPages(@RequestBody JSONObject jsonObject, HttpServletRequest httpServletRequest) {
        HashMap<String, Integer> pageInfo = PageUtil.getPageInfo(jsonObject, httpServletRequest, userService);
        Integer id = pageInfo.get("id");
        Integer pageIndex = pageInfo.get("pageIndex");
        Integer sizePerPage = pageInfo.get("sizePerPage");


        return sheetService.getSheetsInPages(id, pageIndex, sizePerPage);
    }


}
