package ruangong.root.utils;


import ruangong.root.bean.Result;

public class ResultUtil {
    public static void quickSet(Result result, Integer code, String message, Object data){
        result.setErrorCode(code);
        result.setMessage(message);
        result.setData(data);
    }
}
