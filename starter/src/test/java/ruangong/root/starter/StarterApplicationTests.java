package ruangong.root.starter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruangong.root.bean.Template;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.service_xiao.impl.TemplateServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class StarterApplicationTests {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private Template template;
//    @Autowired
//    private TemplateMapper templateMapper;

    @Test
    void contextLoads() {

//        Template template = new Template();
//
//        TemplateService templateService = new TemplateServiceImpl();


        JSONObject object = JSONUtil.createObj().putOnce("1", "1").putOnce("2", "2");
        String data = JSONUtil.toJsonStr(object);
        template.setData(data);
        template.setType(1);
        template.setName("test");
        template.setTime(new Date());
        System.out.println(template);
        templateService.createTemplateByBean(template);

//        QueryWrapper<Template> query = Wrappers.query();
//        query.eq("name","test");
//        Template template1 = templateMapper.selectOne(query);
//        System.out.println(template1);




    }


}
