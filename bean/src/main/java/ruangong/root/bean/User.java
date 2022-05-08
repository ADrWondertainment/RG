package ruangong.root.bean;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "users",schema = "ruangong")
@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String email;
    private String pass;
    private JSONObject templates;
    private JSONObject sheets;
}
