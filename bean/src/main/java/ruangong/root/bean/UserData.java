package ruangong.root.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * @author tao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Scope("prototype")
public class UserData extends CompanyUser{
    Integer cid;
    Integer rid;
    Integer did;
    Integer typeId;
}
