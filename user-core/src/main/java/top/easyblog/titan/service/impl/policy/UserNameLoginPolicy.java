package top.easyblog.titan.service.impl.policy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.RegisterDetailsBean;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessAccountService;
import top.easyblog.titan.service.data.AccessUserService;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-01-29 20:54
 */
@Slf4j
public class UserNameLoginPolicy implements LoginPolicy {

    @Autowired
    private AccessAccountService accessAccountService;
    @Autowired
    private AccessUserService accessUserService;

    @Override
    public LoginDetailsBean doLogin(LoginRequest request) {
        return null;
    }

    @Transaction
    @Override
    public RegisterDetailsBean doRegister(RegisterUserRequest request) {
        //0.前置密码校验
        if (!StringUtils.equals(request.getCredential(), request.getCredentialAgain())) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_EQUAL);
        }
        if (checkPasswordValid(request.getCredential())) {
            throw new BusinessException(ResultCode.PASSWORD_NOT_VALID);
        }
        //1.根据nick_name查询用户信息
        QueryUserRequest queryUserRequest = QueryUserRequest.builder().nickName(request.getIdentifier()).build();
        User user = accessUserService.queryByRequest(queryUserRequest);
        if (Objects.nonNull(user)) {
            //已经存在用户名
            log.info("Error: repeat user_name:{} of user:{}", request.getIdentifier(), user.getId());
            throw new BusinessException(ResultCode.REPEAT_USER_NAME);
        }
        //2.新建用户信息
        CreateUserRequest createUserRequest = CreateUserRequest.builder().nickName(request.getIdentifier()).build();
        User newUser = accessUserService.insertSelective(createUserRequest);

        //3.新建用户账号信息
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .userId(newUser.getId())
                .identityType(request.getIdentifierType().intValue())
                .credential(request.getCredential())
                .verified(Status.ENABLE.getCode())
                .verified(Status.ENABLE.getCode())
                .build();
        accessAccountService.insertSelective(createAccountRequest);
        return null;
    }


}
