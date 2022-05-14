package ruangong.root.controller_xiao;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;
import ruangong.root.bean.UrlResourcedLocation;
import ruangong.root.service_xiao.SheetService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    @Resource
    private SheetService sheetService;


    @PostMapping
    public Result debutSheet(@RequestBody Sheet sheet) {
        System.out.println(1);

        return sheetService.fastCreateSheet(sheet);
    }


    @PostMapping("/url")
    public Result enableSheetUrl(@RequestBody UrlResourcedLocation urlResourcedLocation) {
        return sheetService.updateLocatedUrl(urlResourcedLocation);

    }



}
