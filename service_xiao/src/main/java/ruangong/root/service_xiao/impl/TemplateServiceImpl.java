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
import ruangong.root.service_xiao.PageUtil;
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

        String type = jsonBeanTemplate.getType();
        if (!("0".equals(type) || "1".equals(type))) {
            throw new FrontException(ErrorCode.TEMPLATE_DATA_NULL, "模板名称和内容不能为空捏~");
        }
        QueryWrapper<Template> queryWrapper = Wrappers.query();
        queryWrapper.eq("id", template.getId()).eq("name", template.getName());
        Integer uniqueCheck = templateMapper.selectCount(queryWrapper);
        if (uniqueCheck != 0) {
            throw new FrontException(ErrorCode.TEMPLATE_NAME_DUPLICATED, "模板重名了捏，请重新起一个名字~");
        }
        boolean isUpdated = saveOrUpdate(template);
        if (!isUpdated) {
            throw new BackException(ErrorCode.TEMPLATE_INSERT_FAILURE, "添加或者修改失败了捏，请稍后再试~");
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

        int deleteCheck = templateMapper.deleteById(id);
        if (deleteCheck != 1) {
            throw new BackException(ErrorCode.TEMPLATE_DELETE_FAILURE, "您的模板删除失败捏~");
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

        JSONArray recordsById = PageUtil.getPageRecordsById(id, pageNum, size, "uid", Template.class, templateMapper);
        List<Template> records = JSONUtil.toList(recordsById, Template.class);
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
