package ruangong.root.service_xiao;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruangong.root.bean.Template;

import java.util.Date;

@SpringBootTest(classes = ServiceXiaoApplicationTests.class)

class ServiceXiaoApplicationTests {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private Template template;

    @Test
    void contextLoads() {
        template.setDescription("test");
        template.setName("test");
        template.setTime(new Date());
        Integer[] ints = {1, 2, 3};
        JSONObject object = JSONUtil.parseObj(ints);
//        template.setData(object);
        template.setType(0);

        templateService.createTemplateByBean(template);

    }

}
