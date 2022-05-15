package ruangong.root.bean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
@Scope("prototype")
public class AnswerList {

    private List<Answer> list;

}
