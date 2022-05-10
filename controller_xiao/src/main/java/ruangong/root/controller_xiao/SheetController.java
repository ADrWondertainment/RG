package ruangong.root.controller_xiao;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ruangong.root.bean.Sheet;

@RestController
@RequestMapping("/sheets")
public class SheetController {

    @PostMapping
    public void debutSheet(@RequestBody Sheet sheet){

    }

}
