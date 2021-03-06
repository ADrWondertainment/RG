package ruangong.root.service_xiao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_tao.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


/**
 * @author pangx
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class PageUtil {


    /**
     * 接收json格式如下
     * {
     * "pageNum":1,
     * "size":5
     * }
     */
    public static HashMap<String, Integer> getPageInfo(JSONObject jsonObject, HttpServletRequest httpServletRequest, UserService userService) {

        Integer uid = (Integer) httpServletRequest.getSession().getAttribute("id");
        if (uid == null || uid == 0) {
            throw new BackException(ErrorCode.USER_ILLEGAL_ACCESS, "用户未登录");
        }

        Integer pageNum = (Integer) jsonObject.get("pageNum");
        Integer size = (Integer) jsonObject.get("size");

        HashMap<String, Integer> map = new HashMap<>(10);
        map.put("uid", uid);
        map.put("pageIndex", pageNum);
        map.put("sizePerPage", size);

        return map;
    }

    public static <T> JSONArray getPageRecordsById(Integer id, Integer pageIndex, Integer sizePerPage, String columnName, Class<T> tClass, BaseMapper<T> baseMapper) {

        IPage<T> page = new Page(pageIndex, sizePerPage);
        QueryWrapper<T> query = Wrappers.query();
        query.eq(columnName, id);
        IPage<T> sheetIPage = null;
        try {
            sheetIPage = baseMapper.selectPage(page, query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackException(ErrorCode.SHEET_SELECT_FAILURE, "分页数据查询失败");
        }
        if (sheetIPage.getRecords().size() == 0) {
            throw new BackException(ErrorCode.SHEET_SELECT_FAILURE, "分页数据查询失败");
        }
        return JSONUtil.parseArray(sheetIPage.getRecords());


    }

    public static <T> JSONArray getPageRecordsById(Integer[] ids, Integer pageIndex, Integer sizePerPage, String[] columnNames, Class<T> tClass, BaseMapper<T> baseMapper) {

        if (ids.length != columnNames.length) {
            throw new BackException(ErrorCode.UTIL_ERROR, "分页工具类错误，ids与columns数量不匹配");
        }

        IPage<T> page = new Page(pageIndex, sizePerPage);
        QueryWrapper<T> query = Wrappers.query();

        int length = ids.length;
        for (int i = 0; i < length; i++) {
            query.eq(columnNames[i], ids[i]);
        }

        IPage<T> sheetIPage = baseMapper.selectPage(page, query);
        if (sheetIPage == null) {
            throw new BackException(ErrorCode.SHEET_SELECT_FAILURE, "分页数据查询失败");
        }
        return JSONUtil.parseArray(sheetIPage.getRecords());


    }

}
