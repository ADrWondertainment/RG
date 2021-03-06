package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
public class GroupDetailJava {

    private Integer id;
    private Integer level;
    private List<Cuser> member;

}
