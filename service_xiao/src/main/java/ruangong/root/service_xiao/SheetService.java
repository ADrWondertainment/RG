package ruangong.root.service_xiao;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;

/**
 * @author pangx
 */
@Service
public interface SheetService extends IService<Sheet> {

    /**
     * 快速创建一个Sheet对象并插入sheet表中
     *
     * @param sheet 插入的sheet对象
     * @param uid   进行插入过程的用户id
     * @param cid   所属公司id
     * @return 返回插入id
     */
    Result fastCreateSheet(Sheet sheet, Integer uid, Integer cid);

    /**
     * 通过sheetId查询sheet
     *
     * @param id sheetId
     * @return 对应sheet对象的Json字符串形式
     */
    Result getSheetById(int id);

    /**
     * 通过userId查询该用户的sheets并以分页形式返回
     *
     * @param id          用户id
     * @param pageIndex   查询第几页
     * @param sizePerPage 一页有多少条数据
     * @return IPage<Sheet>.getRecords()的Json字符串形式
     */
    Result getSheetsInPages(Integer id, Integer pageIndex, Integer sizePerPage);

    /**
     * 通过userId查询该用户的sheets并以分页形式返回
     *
     * @param id          用户id
     * @param pageIndex   查询第几页
     * @param sizePerPage 一页有多少条数据
     * @return 返回的是用户所有的问卷表信息
     */
    Result getQueriesInPages(Integer id, Integer pageIndex, Integer sizePerPage);

    /**
     * 通过userId查询该用户的sheets并以分页形式返回
     *
     * @param id          用户id
     * @param pageIndex   查询第几页
     * @param sizePerPage 一页有多少条数据
     * @return 返回的是用户所有的审批表信息
     */
    Result getApprovalsInPages(Integer id, Integer pageIndex, Integer sizePerPage);

    /**
     * 通过answerId来对审批表进行审批，通过或不通过
     *
     * @param id   answerId
     * @param pass 0：不通过，1：通过
     * @return data:1 代表成功审批
     */
    Result checkSheetAnswer(Integer id, Integer pass);
}
