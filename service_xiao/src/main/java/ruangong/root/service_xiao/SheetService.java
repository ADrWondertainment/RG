package ruangong.root.service_xiao;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;
import ruangong.root.bean.UrlResourcedLocation;

@Service
public interface SheetService extends IService<Sheet> {

    public Result fastCreateSheet(Sheet sheet);

    public Result updateLocatedUrl(UrlResourcedLocation urlResourcedLocation);

    public Result getSheetById(int id);

    public Result updateSheet(Sheet sheet);
}
