package ruangong.root.service_xiao.impl;

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
import ruangong.root.bean.JsonBeanTemplate;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.bean.TemplateTransfer;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.exception.BackException;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;


    @Resource
    private Result result;


    @Override
    public Result createOrUpdateTemplateByBean(Template template, JsonBeanTemplate jsonBeanTemplate) {

        JSONObject data = JSONUtil.parseObj(template.getData());
        int type = jsonBeanTemplate.getType();

        if (type != 0 && type != 1) {
            throw new FrontException(ErrorCode.TEMPLATE_DATA_NULL, "模板名称和内容不能为空捏~");
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_DATA_NULL,
//                    "模板名称和内容不能为空捏~",
//                    null
//            );
//            return result;
        }
        QueryWrapper<Template> queryWrapper = Wrappers.query();
        queryWrapper.eq("name", template.getName());

        Template uniqueCheck = templateMapper.selectOne(queryWrapper);


        if (uniqueCheck != null) {
            throw new FrontException(ErrorCode.TEMPLATE_NAME_DUPLICATED, "模板重名了捏，请重新起一个名字~");
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_NAME_DUPLICATED,
//                    "模板重名了捏，请重新起一个名字~",
//                    null
//            );
//            return result;
        }

        boolean isUpdated = saveOrUpdate(template);

        if (!isUpdated) {
            throw new BackException(ErrorCode.TEMPLATE_INSERT_FAILURE, "添加或者修改失败了捏，请稍后再试~");
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_INSERT_FAILURE,
//                    "添加或者修改失败了捏，请稍后再试~",
//                    null
//            );
//            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.TEMPLATE_INSERT_SUCCESS,
                "模板添加/修改成功了捏~",
                JSONUtil.toJsonPrettyStr(JSONUtil.createObj().putOnce("isSuccess", true))
        );

        return result;
    }

    @Override
    public Result deleteTemplateById(Integer id) {

        QueryWrapper<Template> queryWrapper = Wrappers.query();
        queryWrapper.eq("id", id);
        long count = (long) templateMapper.selectCount(queryWrapper);
        if (count != 1) {
            throw new BackException(ErrorCode.TEMPLATE_ID_UNREGISTERED, "没有找到您的模板捏~");
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_ID_UNREGISTERED,
//                    "没有找到您的模板捏~",
//                    null
//            );
//
//            return result;
        }
        int deleteCheck = templateMapper.deleteById(id);
        if (deleteCheck != 1) {
            throw new BackException(ErrorCode.TEMPLATE_DELETE_FAILURE, "您的模板删除失败捏~");
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_DELETE_FAILURE,
//                    "您的模板删除失败捏~",
//                    null
//            );
//
//            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "您的模板删除成功捏~",
                JSONUtil.toJsonPrettyStr(JSONUtil.createObj().putOnce("isSuccess", true))
        );

        return result;
    }

    @Override
    public Result getTemplatesInPages(Integer id, Integer pageNum, Integer size) {

        IPage<Template> page = new Page(pageNum, size);

        QueryWrapper<Template> query = Wrappers.query();
        query.eq("uid", id);
        IPage<Template> templateIPage = templateMapper.selectPage(page, query);
        if (templateIPage == null) {
            throw new BackException(ErrorCode.TEMPLATE_SELECT_FAILURE, "分页数据查询失败");
        }

        List<Template> records = templateIPage.getRecords();
        List<TemplateTransfer> transfers = new ArrayList<>();
        for (Template t : records) {
            TemplateTransfer temp = new TemplateTransfer();
            temp.setId(t.getId());
            temp.setUid(t.getUid());
            temp.setName(t.getName());
            temp.setTime(t.getTime());
            temp.setLength(t.getLength());
            temp.setDescription(t.getDescription());
            temp.setData(JSONUtil.parseObj(t.getData()));
            transfers.add(temp);
        }
        JSONArray jsonArray = JSONUtil.parseArray(transfers);

        System.out.println(JSONUtil.toJsonPrettyStr(jsonArray));

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "查询成功",
                JSONUtil.toJsonPrettyStr(jsonArray)
        );


        return result;
    }

    @Override
    public Result getTemplateById(Integer id) {

        QueryWrapper<Template> query = Wrappers.query();
        query.eq("id", id);
        Template template = templateMapper.selectOne(query);

        if (template == null) {
            throw new BackException(ErrorCode.TEMPLATE_SELECT_FAILURE, "没有对应id的模板");
        }

        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "获取成功",
                JSONUtil.toJsonPrettyStr(template)
        );

        return result;
    }


}
