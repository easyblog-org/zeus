package top.easyblog.titan.feign.internal;

import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.response.ResultCode;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author: frank.huang
 * @date: 2021-11-14 21:29
 */
public interface Verify {

    /**
     * 使用request方法进行feign调用
     *
     * @param request
     * @param <T>
     * @return
     */
    default <T> T request(Supplier<Response<T>> request) {
        Response<T> response = request.get();
        this.throwIfFail(response, ResultCode.REMOTE_INVOKE_FAIL);
        return response.data();
    }

    default <T> void throwIfFail(Response<T> response, ResultCode resultCode) {
        if (Objects.isNull(response) || !response.isSuccess()) {
            throw new BusinessException(resultCode, response.message());
        }
    }

}
