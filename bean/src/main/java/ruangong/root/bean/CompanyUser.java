package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author tao
 */
@Data
@Component
@Scope("prototype")
public class CompanyUser {
    Integer id;
    String email;
    String role;
    String department;
    String company;
    Integer level;
}
