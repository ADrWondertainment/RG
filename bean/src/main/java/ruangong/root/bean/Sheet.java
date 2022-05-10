package ruangong.root.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@TableName("sheets")
@Component
@Scope("prototype")
public class Sheet {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer tid;
    private String name;
    private String description;
    private Date start;
    private Date end;
    private String url;
    private String data;
    private Integer type;
    private Integer length;

}
