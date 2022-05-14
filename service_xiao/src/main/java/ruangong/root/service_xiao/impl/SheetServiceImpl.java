package ruangong.root.service_xiao.impl;


import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.Result;
import ruangong.root.bean.Sheet;
import ruangong.root.bean.Template;
import ruangong.root.bean.UrlResourcedLocation;
import ruangong.root.dao.SheetMapper;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_xiao.SheetService;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import java.util.function.Consumer;

@Service
@Transactional
public class SheetServiceImpl extends ServiceImpl<SheetMapper, Sheet> implements SheetService {

    @Resource
    private SheetMapper sheetMapper;

    @Resource
    private Result result;

    @Resource
    private Sheet sheet;

    @Resource
    private TemplateService templateService;

    public Result fastCreateSheet(Sheet sheet, int uid) {

        Result sheetTemplate = templateService.getTemplateById(sheet.getId());
        Template template = (Template) sheetTemplate.getData();
        String data = template.getData();
        sheet.setLocation(IdUtil.simpleUUID());
        sheet.setUid(uid);

        Integer insertCheck = sheetMapper.insert(sheet);
        System.out.println(sheet.getId());
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "快速添加成功",
                JSONUtil.toJsonPrettyStr(JSONUtil.createObj().putOnce("insertId", sheet.getId()))
        );

        return result;
    }

    @Override
    public Result updateLocatedUrl(UrlResourcedLocation urlResourcedLocation) {

        String url = urlResourcedLocation.getUrl();
        Integer id = urlResourcedLocation.getId();

        sheet.setUrl(url);
        sheet.setId(id);

        sheetMapper.updateById(sheet);

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "url增加成功",
                JSONUtil.toJsonPrettyStr(JSONUtil.createObj().putOnce("updated", true))
        );

        return result;
    }

    @Override
    public Result getSheetById(int id) {
        Sheet sheet = sheetMapper.selectById(id);
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(sheet)
        );

        return result;
    }

    @Override
    public Result updateSheet(Sheet sheet) {

        sheetMapper.updateById(sheet);

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "增加成功",
                JSONUtil.toJsonPrettyStr(JSONUtil.createObj().putOnce("updated", true))
        );


        return result;
    }

    @Override
    public Result getSheetsInPages(Integer id, Integer pageIndex, Integer sizePerPage) {
        IPage<Sheet> page = new Page(pageIndex, sizePerPage);

        QueryWrapper<Sheet> query = Wrappers.query();
        query.eq("uid", id);
        IPage<Sheet> sheetIPage = sheetMapper.selectPage(page, query);

        JSONArray jsonArray = JSONUtil.parseArray(sheetIPage);


        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(jsonArray)
        );


        return result;
    }


}
