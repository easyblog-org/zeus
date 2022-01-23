package top.easyblog.titan.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.easyboot.exception.SignatureArgumentException;
import top.easyboot.exception.SignatureException;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.BaseResponse;
import top.easyblog.titan.response.ResultCode;

/**
 * @author: frank.huang
 * @date: 2021-11-01 17:40
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalConsumeExceptionHandler {

    /**
     * 处理未捕获的Exception
     *
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> handleException(Exception e) {
        e.printStackTrace();
        return BaseResponse.fail(ResultCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理未捕获的RuntimeException
     *
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<Object> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return BaseResponse.fail(ResultCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理未捕获的自定义业务异常BusinessException
     *
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Object> handleBusinessException(BusinessException e) {
        e.printStackTrace();
        return BaseResponse.fail(e.getCode(), e.getMessage());
    }


    /**
     * 处理未捕获的自定义业务异常 SignatureArgumentException、SignatureException
     *
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler({SignatureArgumentException.class,SignatureException.class})
    public BaseResponse<Object> handleSignException(RuntimeException e) {
        return BaseResponse.fail(ResultCode.SIGN_FAIL.getCode(), e.getMessage());
    }

    /**
     * 捕获数据库异常
     * @param e
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public BaseResponse<Object> handleDataBaseException(DataAccessException e){
        return BaseResponse.fail(ResultCode.DATA_ACCESS_FAIL.getCode(),e.getCause().toString());
    }
}
