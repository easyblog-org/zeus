package top.easyblog.titan.service.auth.policy;

import org.apache.commons.lang3.StringUtils;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.enums.AccountStatus;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserService;
import top.easyblog.titan.util.EncryptUtils;

import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2022-02-13 21:55
 */
public abstract class AbstractLoginStrategy implements LoginStrategy {

    protected AccountService accountService;

    protected UserService userService;

    protected RandomNicknameService randomNicknameService;


    public AbstractLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService) {
        this.accountService = accountService;
        this.userService = userService;
        this.randomNicknameService = randomNicknameService;
    }

    /**
     * 登录前置校验
     *
     * @param request
     * @return
     */
    public UserDetailsBean preLoginVerify(LoginRequest request) {
        QueryAccountRequest queryAccountRequest = QueryAccountRequest.builder()
                .identityType(request.getIdentifierType().intValue())
                .identifier(request.getIdentifier())
                .build();
        AccountBean accountBean = accountService.queryAccountDetails(queryAccountRequest);
        if (Objects.isNull(accountBean)) {
            //check and found that the account is not exists
            throw new BusinessException(ResultCode.USER_ACCOUNT_NOT_FOUND);
        }

        if (AccountStatus.PRE_ACTIVE.getCode().equals(accountBean.getStatus())) {
            //账户未激活
            throw new BusinessException(ResultCode.ACCOUNT_IS_PRE_ACTIVE);
        }
        if (AccountStatus.DELETE.getCode().equals(accountBean.getStatus())) {
            //用户账户已经删除
            throw new BusinessException(ResultCode.ACCOUNT_IS_DELETE);
        }
        if (AccountStatus.FREEZE.getCode().equals(accountBean.getStatus())) {
            //账户被封还未解封
            throw new BusinessException(ResultCode.ACCOUNT_IS_FREEZE);
        }
        return UserDetailsBean.builder().currAccount(accountBean).build();
    }

    /**
     * 默认用户名密码登录
     *
     * @param request
     * @return
     */
    public UserDetailsBean processLogin(LoginRequest request) {
        UserDetailsBean userDetailsBean = preLoginVerify(request);
        AccountBean currAccount = userDetailsBean.getCurrAccount();
        //check request password and database password
        String databasePassword = currAccount.getCredential();
        String requestPassword = encryptPassword(request.getCredential());
        if (StringUtils.isEmpty(requestPassword) || Boolean.FALSE.equals(requestPassword.equalsIgnoreCase(databasePassword))) {
            throw new BusinessException(ResultCode.PASSWORD_VALID_FAILED);
        }
        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(currAccount.getUserId())
                .sections(LoginConstants.QUERY_HEADER_IMG)
                .build());
    }

    /**
     * 注册时创建User以及Account
     *
     * @param request
     * @return
     */
    public UserDetailsBean processRegister(RegisterUserRequest request) {
        //3.创建User
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .nickName(randomNicknameService.getRandomNickname())
                .active(AccountStatus.PRE_ACTIVE.getCode())
                .build();
        User newUser = userService.createUser(createUserRequest);

        //4. 创建账户并绑定user_id
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .userId(newUser.getId())
                .identityType((int) request.getIdentifierType())
                .identifier(request.getIdentifier())
                .credential(encryptPassword(request.getCredential()))
                .verified(Status.DISABLE.getCode())
                .status(AccountStatus.PRE_ACTIVE.getCode())
                .createDirect(Boolean.TRUE)
                .build();
        accountService.createAccount(createAccountRequest);
        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(newUser.getId())
                .sections(LoginConstants.QUERY_ACCOUNTS)
                .build());
    }

    /**
     * 校验密码是否合法
     * 1.位数限制：6~12位
     * 2.字符种类限制：字母和数字以及特殊字符($,%,@,^,&)混合加密，必须出现任意两个
     *
     * @param password
     * @return
     */
    public boolean checkPasswordValid(String password) {
        if (!StringUtils.isNotBlank(password)) {
            return false;
        }
        int len = 0;
        if ((len = password.length()) < LoginConstants.PASSWORD_MIN_LEN || len > LoginConstants.PASSWORD_MAX_LEN) {
            return false;
        }
        char[] chars = password.toCharArray();
        int[] strength = new int[3];   //密码强度
        int specialFlag = 2, letterFlag = 1, numberFlag = 0;
        for (char ch : chars) {
            if (strength[specialFlag] == 0 && LoginConstants.PASSWORD_SPECIAL_CHARACTERS.contains(String.valueOf(ch))) {
                strength[specialFlag] = 1;
            } else if (strength[letterFlag] == 0 && ((ch >= 'a' && ch <= 'z') || ch >= 'A' && ch <= 'Z')) {
                strength[letterFlag] = 1;
            } else if (strength[numberFlag] == 0 && ch >= '0' && ch <= '9') {
                strength[numberFlag] = 1;
            }
        }

        return strength[specialFlag] + strength[letterFlag] + strength[numberFlag] >= 2;
    }

    public String encryptPassword(String originalPassword) {
        return EncryptUtils.SHA256(originalPassword, Constants.USER_PASSWORD_SECRET_KEY);
    }
}
