package top.easyblog.titan.service;

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
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.atomic.AtomicSignInLogService;

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
    private AtomicSignInLogService atomicSignInLogService;

    @Transaction
    public SignInLogBean createSignInLog(CreateSignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        SignInLog signInLog = atomicSignInLogService.insertSignInLogByRequest(request);
        SignInLogBean signInLogBean = new SignInLogBean();
        BeanUtils.copyProperties(signInLog, signInLogBean);
        return signInLogBean;
    }

    @Transaction
    public SignInLogBean querySignInLogDetails(QuerySignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        SignInLog signInLog = atomicSignInLogService.querySignLogByRequest(request);
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
        long count = atomicSignInLogService.countByRequest(request);
        if (count == 0) {
            return response;
        }
        response.setTotal(count);
        response.setList(atomicSignInLogService.querySignInLogListByRequest(request).stream().map(signInLog -> {
            SignInLogBean signInLogBean = new SignInLogBean();
            BeanUtils.copyProperties(signInLog, signInLogBean);
            return signInLogBean;
        }).collect(Collectors.toList()));
        return response;
    }


    @Transaction
    public void updateSignLog(UpdateSignInLogRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        atomicSignInLogService.updateSignInLogByPrimarySelective(request);
    }

}
