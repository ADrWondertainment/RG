package ruangong.root.bean;


import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
public class JsonBeanTemplateContentsContent {
    private String id;
    private String type;
    private Map<String, Integer> value;
    private String description;

}
