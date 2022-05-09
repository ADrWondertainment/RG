package ruangong.root.service_xiao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.exception.ErrorCode;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;

@Service
@Transactional
@MapperScan(basePackages = "ruangong.root.dao")
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private Result result;


    @Override
    public Result createTemplateByBean(Template template) {

        int dataCheck;
        dataCheck = (template.getData() == null || (template.getType() != 0 && template.getType() != 1) ? 0 : 1);

        if (dataCheck == 0) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.TEMPLATE_DATA_NULL,
                    "模板名称和内容不能为空捏~",
                    null
            );
            return result;
        }
        QueryWrapper<Template> queryWrapper = Wrappers.query();
        queryWrapper.eq("name", template.getName());

        Template uniqueCheck = templateMapper.selectOne(queryWrapper);


        if (uniqueCheck != null) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.TEMPLATE_NAME_DUPLICATED,
                    "模板重名了捏，请重新起一个名字~",
                    null
            );
            return result;
        }

        boolean isUpdated = saveOrUpdate(template);

        if (isUpdated) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.TEMPLATE_INSERT_FAILURE,
                    "添加或者修改失败了捏，请稍后再试~",
                    null
            );
            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.TEMPLATE_INSERT_SUCCESS,
                "模板添加/修改成功了捏~",
                1
        );

        return result;
    }

    @Override
    public Result deleteTemplateById(Integer id) {

        QueryWrapper<Template> queryWrapper = Wrappers.query();
        queryWrapper.eq("id", id);
        Integer count = templateMapper.selectCount(queryWrapper);
        if (count != 1) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.TEMPLATE_ID_UNREGISTERED,
                    "没有找到您的模板捏~",
                    null
            );

            return result;
        }
        int deleteCheck = templateMapper.deleteById(id);
        if (deleteCheck != 1) {
            ResultUtil.quickSet(
                    result,
                    ErrorCode.TEMPLATE_DELETE_FAILURE,
                    "您的模板删除失败捏~",
                    null
            );

            return result;
        }
        ResultUtil.quickSet(
                result,
                ErrorCode.ALL_SET,
                "您的模板删除成功捏~",
                1
        );

        return result;
    }


}
