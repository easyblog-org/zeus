package top.easyblog.titan.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessAccountService;

/**
 * @author frank.huang
 * @date 2022/01/30 13:33
 */
@Service
public class UserAccountService {

    @Autowired
    private AccessAccountService accessAccountService;

    public AccountBean queryAccountByRequest(QueryAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        Account account = accessAccountService.queryAccountByRequest(request);
        if (Objects.isNull(account)) {
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_FOUND);
        }
        AccountBean accountBean = new AccountBean();
        BeanUtils.copyProperties(account, accountBean);
        return accountBean;
    }

}
