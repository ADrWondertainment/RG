package ruangong.root.bean;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Scope("prototype")
public class TemplateTransfer {
    private Integer id;
    private Integer uid;
    private String description;
    private String name;
    private Date time;
    private JSONObject data;
    private Integer length;
}
