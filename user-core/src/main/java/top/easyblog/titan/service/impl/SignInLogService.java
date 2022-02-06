package top.easyblog.titan.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.dao.auto.model.SignInLog;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.QuerySignInLogRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessSignInLogService;

/**
 * @author frank.huang
 * @date 2022/01/30 13:34
 */
@Service
public class SignInLogService {

    @Autowired
    private AccessSignInLogService accessSignInLogService;

    public SignInLogBean querySignInLogDetailsByRequest(QuerySignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        SignInLog signInLog = accessSignInLogService.querySignLogByRequest(request);
        if (Objects.isNull(signInLog)) {
            throw new BusinessException(ResultCode.SIGN_IN_LOG_NOT_FOUND);
        }

        SignInLogBean signInLogBean = new SignInLogBean();
        BeanUtils.copyProperties(signInLog, signInLogBean);
        return signInLogBean;
    }

}
