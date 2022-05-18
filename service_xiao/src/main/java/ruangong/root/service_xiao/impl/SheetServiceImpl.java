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
import ruangong.root.service_xiao.PageUtil;
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

        sheet.setLocation(IdUtil.simpleUUID());
        sheet.setUid(uid);
        sheet.setCid(cid);

        Result templateById = templateService.getTemplateById(sheet.getTid());
        Template templateFromData = ResultUtil.getBeanFromData(templateById, Template.class);
        sheet.setLength(templateFromData.getLength());
        String type = (String) JSONUtil.parseObj(templateFromData.getData()).get("type");

        sheet.setType(Integer.parseInt(type));


        int insertCheck = sheetMapper.insert(sheet);
        if (insertCheck != 1) {
            throw new BackException(ErrorCode.FAST_CREAT_SHEET_FAILURE, "快速创建sheet失败");
        }

        sheet.setUrl("http://localhost:9090/#/justFillForm/" + sheet.getId());

        sheetMapper.updateById(sheet);

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "快速添加成功",
                JSONUtil.toJsonPrettyStr(JSONUtil.createObj().putOnce("insertId", sheet.getId()))
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


//        IPage<Sheet> page = new Page(pageIndex, sizePerPage);
//        QueryWrapper<Sheet> query = Wrappers.query();
//        query.eq("uid", id);
//        IPage<Sheet> sheetIPage = sheetMapper.selectPage(page, query);
//        if (sheetIPage == null) {
//            throw new BackException(ErrorCode.SHEET_SELECT_FAILURE, "分页数据查询失败");
//        }
//        JSONArray jsonArray = JSONUtil.parseArray(sheetIPage.getRecords());

        JSONArray pageRecordsById = PageUtil.getPageRecordsById(id, pageIndex, sizePerPage, "uid", Sheet.class, sheetMapper);
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(pageRecordsById)
        );


        return result;
    }

    @Override
    public Result checkSheetAnswer(Integer id, Integer pass) {
        UpdateWrapper<Answer> update = Wrappers.update();
        update.eq("id", id).set("pass", pass);
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
