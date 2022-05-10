package ruangong.root.controller_xiao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;
import ruangong.root.service_xiao.SheetService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    @Resource
    private SheetService sheetService;

    @PostMapping
    public Result debutSheet(@RequestBody Sheet sheet){
        Result result = sheetService.fastCreateSheet(sheet);
        return result;
    }

    @PostMapping("/url")
    public Result enableSheetUrl(){
        return null;
    }

}
