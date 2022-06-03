package ruangong.root.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
@TableName("cuser_view")
public class CuserView {

    private Integer id;
    private Integer uid;
    private Integer groupId;
    private String email;
    private String role;
    private String dept;
}
