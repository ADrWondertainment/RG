package ruangong.root.bean;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.ObjectTypeHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pangx
 */
@Component
@Scope("prototype")
@Data
@Accessors(chain = true)
@TableName(value = "group_details", autoResultMap = true)
public class GroupDetail {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String member;

    private Integer level;

    public static GroupDetailJava groupDetailJavaFactory(GroupDetail groupDetail) {
        GroupDetailJava groupDetailJava = new GroupDetailJava();
        groupDetailJava.setId(groupDetail.getId());
        groupDetailJava.setLevel(groupDetail.getLevel());
        List<Cuser> cusers = JSONUtil.toList(groupDetail.getMember(), Cuser.class);
        groupDetailJava.setMember(cusers);
        return groupDetailJava;
    }
}
