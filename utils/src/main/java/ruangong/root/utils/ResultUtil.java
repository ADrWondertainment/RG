package ruangong.root.utils;


import cn.hutool.json.JSONUtil;
import ruangong.root.bean.Result;

public class ResultUtil {


    public static void quickSet(Result result, Integer code, String message, Object data) {
        result.setErrorCode(code);
        result.setMessage(message);
        result.setData(data);
    }

    public static <T> T getBeanFromData(Result result, Class<T> tClass) {
        String data = (String) result.getData();
        return JSONUtil.toBean(data, tClass);
    }

}
