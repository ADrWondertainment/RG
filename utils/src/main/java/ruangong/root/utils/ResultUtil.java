package ruangong.root.utils;


import cn.hutool.json.JSONUtil;
import ruangong.root.bean.Result;
import ruangong.root.exception.ErrorCode;
import ruangong.root.exception.FrontException;

import java.util.List;

/**
 * @author pangx
 */
public class ResultUtil {


    public static void quickSet(Result result, Integer code, String message, Object data) {
        result.setErrorCode(code);
        result.setMessage(message);
        result.setData(data);
    }

    public static <T> T getBeanFromData(Result result, Class<T> tClass) {
        String data = (String) result.getData();
        T t = JSONUtil.toBean(data, tClass);
        if (t == null) {
            throw new FrontException(ErrorCode.FRONT_DATA_IRREGULAR, "前端数据格式不规范");
        }
        return t;
    }

    public static <T> List<T> getListFromData(Result result, Class<T> tClass){
        String data = (String) result.getData();
        T t = JSONUtil.toBean(data, tClass);
        if (t == null) {
            throw new FrontException(ErrorCode.FRONT_DATA_IRREGULAR, "前端数据格式不规范");
        }
        return null;


    }

}
