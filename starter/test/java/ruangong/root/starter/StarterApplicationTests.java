package ruangong.root.starter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ruangong.root.bean.Result;
import ruangong.root.bean.Template;
import ruangong.root.service_xiao.TemplateService;

import java.util.Date;

@SpringBootTest
class StarterApplicationTests {
    @Autowired
    private TemplateService templateService;

    @Autowired
    private Template template;

    @Test
    void InsertOrUpdateTest() {

        template.setDescription("test");
        template.setName("test2");
        template.setType(0);
        template.setDescription("test2");
        template.setTime(new Date());
        JSONObject entries = JSONUtil.createObj().putOnce("1", "3");
        String s = JSONUtil.toJsonStr(entries);
        template.setData(s);
        templateService.createTemplateByBean(template);
        System.out.println(template.getId());

    }

    @Test
    void deleteTest(){
        Result result = templateService.deleteTemplateById(2);
        System.out.println(JSONUtil.toJsonPrettyStr(result));
    }

}
