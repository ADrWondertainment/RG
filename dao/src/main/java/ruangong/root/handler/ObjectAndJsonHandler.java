package ruangong.root.handler;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import ruangong.root.bean.Cuser;
import ruangong.root.bean.GroupDetail;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author pangx
 */
public class ObjectAndJsonHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        String s = JSONUtil.toJsonPrettyStr(o);
        preparedStatement.setString(i, s);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return JSONUtil.toList(string, Cuser.class);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        return JSONUtil.toList(string, Cuser.class);
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return JSONUtil.toList(string, Cuser.class);
    }
}
