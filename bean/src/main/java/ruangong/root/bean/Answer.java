package ruangong.root.bean;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
@TableName("answers")
public class Answer {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer sid;
    private String data;
    private Integer done;
    private Integer pass;
}
