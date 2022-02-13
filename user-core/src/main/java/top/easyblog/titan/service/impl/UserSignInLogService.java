package top.easyblog.titan.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.dao.auto.model.SignInLog;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateSignInLogRequest;
import top.easyblog.titan.request.QuerySignInLogListRequest;
import top.easyblog.titan.request.QuerySignInLogRequest;
import top.easyblog.titan.request.UpdateSignInLogRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessSignInLogService;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:34
 */
@Service
public class UserSignInLogService {

    @Autowired
    private AccessSignInLogService accessSignInLogService;

    @Transaction
    public void createSignInLog(CreateSignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        accessSignInLogService.insertSignInLogByRequest(request);
    }

    @Transaction
    public SignInLogBean querySignInLogDetails(QuerySignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        SignInLog signInLog = accessSignInLogService.querySignLogByRequest(request);
        if (Objects.isNull(signInLog)) {
            return null;
        }

        SignInLogBean signInLogBean = new SignInLogBean();
        BeanUtils.copyProperties(signInLog, signInLogBean);
        return signInLogBean;
    }

    @Transaction
    public PageResponse<SignInLogBean> querySignInLogList(QuerySignInLogListRequest request) {
        PageResponse<SignInLogBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                0L, Collections.emptyList());
        long count = accessSignInLogService.countByRequest(request);
        if (count == 0) {
            return response;
        }
        response.setTotal(count);
        response.setData(accessSignInLogService.querySignInLogListByRequest(request).stream().map(signInLog -> {
            SignInLogBean signInLogBean = new SignInLogBean();
            BeanUtils.copyProperties(signInLog, signInLogBean);
            return signInLogBean;
        }).collect(Collectors.toList()));
        return response;
    }


    @Transaction
    public void updateSignLog(UpdateSignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        accessSignInLogService.updateSignInLogByRequest(request);
    }

}
