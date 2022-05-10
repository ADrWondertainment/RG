package ruangong.root.utils;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ruangong.root.bean.JsonBeanTemplate;
import ruangong.root.bean.Template;

import javax.annotation.Resource;
import java.util.Date;


public class TemplateUtil {


    public static Template jsonBeanToTemplate(JsonBeanTemplate jsonBeanTemplate){
        Template template = new Template();

        template.setUid(jsonBeanTemplate.getUid());
        template.setDescription(jsonBeanTemplate.getDescription());
        template.setName(jsonBeanTemplate.getName());
        template.setTime(new Date());
        template.setData(JSONUtil.toJsonPrettyStr(jsonBeanTemplate));
        template.setLength(jsonBeanTemplate.getContentLength());

        return template;

    }

    public static Template strToTemplate(String str){
        return jsonBeanToTemplate(JSONUtil.toBean(str,JsonBeanTemplate.class)) ;
    }

}
