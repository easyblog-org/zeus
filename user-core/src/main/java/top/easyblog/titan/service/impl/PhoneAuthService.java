package top.easyblog.titan.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreatePhoneAuthRequest;
import top.easyblog.titan.request.QueryPhoneAuthRequest;
import top.easyblog.titan.request.UpdatePhoneAuthRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessPhoneAuthService;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-10 23:10
 */
@Slf4j
@Service
public class PhoneAuthService {

    @Autowired
    private AccessPhoneAuthService phoneAuthService;

    @Transaction
    public void createPhoneAuth(CreatePhoneAuthRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        PhoneAuth phoneAuth = queryPhoneAuthDetails(QueryPhoneAuthRequest.builder().phoneAreaCode(request.getPhoneAreaCode()).phone(request.getPhone()).build());
        if (Objects.nonNull(phoneAuth)) {
            throw new BusinessException(ResultCode.PHONE_ACCOUNT_ALREADY_EXISTS);
        }
        phoneAuthService.insertByRequestSelective(request);
    }

    @Transaction
    public PhoneAuth queryPhoneAuthDetails(QueryPhoneAuthRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        return phoneAuthService.queryPhoneAuthByRequest(request);
    }

    @Transaction
    public void updatePhoneAuth(UpdatePhoneAuthRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        PhoneAuth phoneAuth = new PhoneAuth();
        BeanUtils.copyProperties(request, phoneAuth);
        phoneAuthService.updatePhoneAuthByRequest(phoneAuth);
    }

}
