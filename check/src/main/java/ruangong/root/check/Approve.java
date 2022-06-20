package ruangong.root.check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ruangong.root.bean.Answer;


@Data
@Component
@Scope("prototype")
@TableName("answers")
public class Approve {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer sid;
    private Integer uid;
    private String data;
    private Integer done;
    private Integer pass;
    private String flow;

    public static Approve transferAnswer(Answer answer) {
        Approve approve = new Approve();
        approve.setId(answer.getId());
        approve.setSid(answer.getSid());
        approve.setUid(answer.getUid());
        approve.setData(answer.getData());
        approve.setDone(answer.getDone());
        approve.setPass(answer.getPass());
        approve.setFlow(answer.getFlow());
        return approve;
    }
}
