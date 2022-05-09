package ruangong.root.service_xiao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    @Qualifier("templateMapper")
    private TemplateMapper templateMapper;

//    @Resource
//    private Result result;


    @Override
    public Result createTemplateByBean(Template template) {


//        int dataCheck;
//        dataCheck = (template.getData() == null || (template.getType() != 0 && template.getType() != 1) ? 0 : 1);

//        QueryWrapper<Template> queryWrapper = Wrappers.query();
//        queryWrapper.eq("name", template.getName());

//        Template uniqueCheck = templateMapper.selectOne(queryWrapper);


//        if (uniqueCheck != null) {
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_NAME_DUPLICATED,
//                    "模板重名了捏，请重新起一个名字~",
//                    null
//            );
//            return result;
//        }
//        int insert = templateMapper.insert(template);
        Template template1 = templateMapper.selectById(1);
//        if (insert != 1) {
//            ResultUtil.quickSet(
//                    result,
//                    ErrorCode.TEMPLATE_INSERT_FAILURE,
//                    "添加失败了捏，请稍后再试~",
//                    null
//            );
//            return result;
//        }
//        ResultUtil.quickSet(
//                result,
//                ErrorCode.TEMPLATE_INSERT_SUCCESS,
//                "模板添加成功了捏~",
//                1
//        );

        return new Result();
    }
}
