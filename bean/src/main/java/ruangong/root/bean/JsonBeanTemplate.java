package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@Data
public class JsonBeanTemplate {
    private int uid;
    private String name;
    private int type;
    private int contentLength;
    private String description;
    private List<JsonBeanTemplateContentsContent> content;
}
