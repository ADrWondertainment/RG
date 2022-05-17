package ruangong.root.bean;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;

@TableName(value = "users",schema = "ruangong")
@Data
@Component
@Scope("prototype")

public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String email;
    private String pass;
    private String templates;
    private String sheets;
    private Integer type;
}
