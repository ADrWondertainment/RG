package ruangong.root.service_xiao.impl;


import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.*;
import ruangong.root.dao.AnswerMapper;
import ruangong.root.dao.SheetMapper;
import ruangong.root.exception.BackException;
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
    private AnswerMapper answerMapper;

    @Resource
    private Result result;

    @Resource
    private Sheet sheet;

    @Resource
    private TemplateService templateService;

    public Result fastCreateSheet(Sheet sheet, Integer cid, Integer uid) {

//        Result sheetTemplate = templateService.getTemplateById(sheet.getTid());
//        Template template = ResultUtil.getBeanFromData(sheetTemplate, Template.class);
//        Template template = (Template) sheetTemplate.getData();
//        String data = template.getData();
        sheet.setLocation(IdUtil.simpleUUID());
        sheet.setUid(uid);
        sheet.setCid(cid);

        Result templateById = templateService.getTemplateById(sheet.getTid());
        Template templateFromData = ResultUtil.getBeanFromData(templateById, Template.class);
        sheet.setLength(templateFromData.getLength());
        Integer type = (Integer) JSONUtil.parseObj(templateFromData.getData()).get("type");
        sheet.setType(type);


        int insertCheck = sheetMapper.insert(sheet);
        if (insertCheck != 1) {
            throw new BackException(ErrorCode.FAST_CREAT_SHEET_FAILURE, "快速创建sheet失败");
        }
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

        if (sheet == null) {
            throw new BackException(ErrorCode.ANSWER_ID_UNREGISTERED, "未找到对应id的sheet");
        }

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
        if (sheetIPage == null) {
            throw new BackException(ErrorCode.SHEET_SELECT_FAILURE, "分页数据查询失败");
        }
        JSONArray jsonArray = JSONUtil.parseArray(sheetIPage.getRecords());


        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(jsonArray)
        );


        return result;
    }

    @Override
    public Result checkSheetAnswer(Integer id, Integer done) {
        UpdateWrapper<Answer> update = Wrappers.update();
        update.eq("id", id).set("done", done);
        int update1 = answerMapper.update(null, update);
        if (update1 != 1) {
            throw new BackException(ErrorCode.PASS_FAILURE, "审批失败");
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "审批成功",
                1
        );

        return result;
    }


}
