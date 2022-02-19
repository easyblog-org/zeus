package top.easyblog.titan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateAccountRequest;
import top.easyblog.titan.request.QueryAccountListRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.UpdateAccountRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessAccountService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:33
 */
@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccessAccountService accessAccountService;

    @Transaction
    public Account createAccount(CreateAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        Account account = null;
        if (Objects.isNull(request.getCreateDirect()) || Boolean.FALSE.equals(request.getCreateDirect())) {
            QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                    .identityType(request.getIdentityType())
                    .identifier(request.getIdentifier()).build();
            account = accessAccountService.queryAccountByRequest(queryAccountRequest);
            if (Objects.nonNull(account)) {
                throw new BusinessException(ResultCode.USER_ACCOUNT_EXISTS);
            }
        }
        account = accessAccountService.insertSelective(request);
        log.info("Create new user account id={} successfully", account.getId());
        return account;
    }

    @Transaction
    public AccountBean queryAccountDetails(QueryAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        Account account = accessAccountService.queryAccountByRequest(request);
        if (Objects.isNull(account)) {
            return null;
        }
        AccountBean accountBean = new AccountBean();
        BeanUtils.copyProperties(account, accountBean);
        return accountBean;
    }

    @Transaction
    public List<AccountBean> queryAccountList(QueryAccountListRequest request) {
        return accessAccountService.queryAccountListByRequest(request).stream().map(account -> {
            AccountBean accountBean = new AccountBean();
            BeanUtils.copyProperties(account, accountBean);
            return accountBean;
        }).collect(Collectors.toList());
    }

    @Transaction
    public void updateAccount(UpdateAccountRequest request) {
        Account account = buildUpdateAccount(request);
        accessAccountService.updateAccountByRequest(account);
    }

    private Account buildUpdateAccount(UpdateAccountRequest request) {
        Account account = new Account();
        if (Objects.nonNull(request.getId())) {
            account.setId(request.getId());
        }
        if (Objects.nonNull(request.getUserId())) {
            account.setUserId(request.getUserId());
        }
        if (Objects.nonNull(request.getStatus())) {
            account.setStatus(request.getStatus());
        }
        if (Objects.nonNull(request.getCredential())) {
            account.setCredential(request.getCredential());
        }
        if (Objects.nonNull(request.getIdentifier())) {
            account.setIdentifier(request.getIdentifier());
        }
        if (Objects.nonNull(request.getIdentityType())) {
            account.setIdentityType(request.getIdentityType());
        }
        if (Objects.nonNull(request.getVerified())) {
            account.setVerified(request.getVerified());
        }
        return account;
    }

}
