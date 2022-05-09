package ruangong.root.starter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruangong.root.bean.Template;
import ruangong.root.dao.TemplateMapper;
import ruangong.root.service_xiao.TemplateService;
import ruangong.root.service_xiao.impl.TemplateServiceImpl;

import java.util.Date;

@SpringBootTest
class StarterApplicationTests {

//    @Autowired
//    private TemplateService templateService;

//    @Autowired
//    private Template template;
    @Autowired
    private TemplateMapper templateMapper;

    @Test
    void contextLoads() {

//        Template template = new Template();
//
//        TemplateService templateService = new TemplateServiceImpl();
//
//        template.setDescription("test");
//        template.setName("test");
//        template.setTime(new Date());
//
//        JSONObject object = JSONUtil.createObj().putOnce("1", "1").putOnce("2", "2");
//        template.setData(object);
//        template.setType(0);
//
//        templateService.createTemplateByBean(template);
        Template template = templateMapper.selectById(1);
        System.out.println(template);


    }


}
