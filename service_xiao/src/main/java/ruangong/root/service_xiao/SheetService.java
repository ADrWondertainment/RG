package ruangong.root.service_xiao;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;
import ruangong.root.bean.User;
import ruangong.root.bean.UrlResourcedLocation;

import java.sql.ResultSet;

@Service
public interface SheetService extends IService<Sheet> {

    public Result fastCreateSheet(Sheet sheet, Integer uid, Integer cid);

    public Result updateLocatedUrl(UrlResourcedLocation urlResourcedLocation);

    public Result getSheetById(int id);

    public Result updateSheet(Sheet sheet);

    public Result getSheetsInPages(Integer id, Integer pageIndex, Integer sizePerPage);

    public Result checkSheetAnswer(Integer id, Integer done);
}
