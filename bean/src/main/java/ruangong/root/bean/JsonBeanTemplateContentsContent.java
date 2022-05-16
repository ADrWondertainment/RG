package ruangong.root.bean;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
@Data
public class JsonBeanTemplateContentsContent {
    private String id;
    private String type;
    private String description;
    private Map<String, Integer> value;
}
