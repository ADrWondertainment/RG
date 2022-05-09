package ruangong.root.bean;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@TableName("templates")
@Component
@Scope("prototype")

public class Template {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String description;
    private String name;
    private Date time;
    private String data;
    private Integer type;
}
