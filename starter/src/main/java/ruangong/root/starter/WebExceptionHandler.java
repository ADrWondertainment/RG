package ruangong.root.starter;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ruangong.root.bean.Result;
import ruangong.root.exception.BackException;
import ruangong.root.exception.FrontException;
import ruangong.root.exception.UnknownException;
import ruangong.root.utils.ResultUtil;

import javax.annotation.Resource;

@RestControllerAdvice
public class WebExceptionHandler {

    @Resource
    private Result result;

    @ExceptionHandler(FrontException.class)
    public Result captureFrontException(FrontException frontException) {

        ResultUtil.quickSet(
                result,
                frontException.getErrorCode(),
                frontException.getErrorMessage(),
                "FrontException"
        );

        return result;
    }

    @ExceptionHandler(BackException.class)
    public Result captureBackException(BackException backException) {

        ResultUtil.quickSet(
                result,
                backException.getErrorCode(),
                backException.getErrorMessage(),
                "BackException"
        );


        return result;
    }

    @ExceptionHandler(UnknownException.class)
    public Result captureUnknownException(UnknownException unknownException) {

        ResultUtil.quickSet(
                result,
                unknownException.getErrorCode(),
                unknownException.getErrorMessage(),
                "UnknownException"
        );


        return result;
    }

}
