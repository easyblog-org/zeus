package top.easyblog.titan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateAccountRequest;
import top.easyblog.titan.request.QueryAccountListRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.request.UpdateAccountRequest;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.atomic.AtomicAccountService;

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
    private AtomicAccountService atomicAccountService;

    @Transaction
    public Account createAccount(CreateAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        Account account = null;
        if (Objects.isNull(request.getCreateDirect()) || Boolean.FALSE.equals(request.getCreateDirect())) {
            QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                    .identityType(request.getIdentityType())
                    .identifier(request.getIdentifier()).build();
            account = atomicAccountService.queryAccountByRequest(queryAccountRequest);
            if (Objects.nonNull(account)) {
                throw new BusinessException(ZeusResultCode.USER_ACCOUNT_EXISTS);
            }
        }
        account = atomicAccountService.insertSelective(request);
        log.info("Create new user account id={} successfully", account.getId());
        return account;
    }

    @Transaction
    public AccountBean queryAccountDetails(QueryAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        Account account = atomicAccountService.queryAccountByRequest(request);
        if (Objects.isNull(account)) {
            return null;
        }
        AccountBean accountBean = new AccountBean();
        BeanUtils.copyProperties(account, accountBean);
        return accountBean;
    }

    @Transaction
    public List<AccountBean> queryAccountList(QueryAccountListRequest request) {
        return atomicAccountService.queryAccountListByRequest(request).stream().map(account -> {
            AccountBean accountBean = new AccountBean();
            BeanUtils.copyProperties(account, accountBean);
            return accountBean;
        }).collect(Collectors.toList());
    }


    public void updateByIdentityType(Long userId, Integer identityType, UpdateAccountRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.INVALID_PARAMS);
        }

        Account oldAccount = atomicAccountService.queryAccountByRequest(QueryAccountRequest.builder()
                .userId(userId).identityType(identityType).build());
        if (Objects.isNull(oldAccount)) {
            throw new BusinessException(ZeusResultCode.USER_ACCOUNT_NOT_FOUND);
        }

        request.setId(oldAccount.getId());
        Account account = buildUpdateAccount(request);
        atomicAccountService.updateAccountByPKSelective(account);
    }

    public void updateAccount(Long accountId, UpdateAccountRequest request) {
        Account oldAccount = atomicAccountService.queryAccountByRequest(QueryAccountRequest.builder().id(accountId).build());
        if (Objects.isNull(oldAccount)) {
            throw new BusinessException(ZeusResultCode.USER_ACCOUNT_NOT_FOUND);
        }

        request.setId(oldAccount.getId());
        Account account = buildUpdateAccount(request);
        atomicAccountService.updateAccountByPKSelective(account);
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
