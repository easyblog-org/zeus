package top.easyblog.titan.service.oauth.impl.policy;

import org.apache.commons.lang3.StringUtils;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.enums.AccountStatus;
import top.easyblog.titan.enums.IdentifierType;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.AccountService;
import top.easyblog.titan.service.RandomNicknameService;
import top.easyblog.titan.service.UserHeaderImgService;
import top.easyblog.titan.service.UserService;
import top.easyblog.titan.service.oauth.ILoginStrategy;
import top.easyblog.titan.util.EncryptUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author: frank.huang
 * @date: 2022-02-13 21:55
 */
public abstract class AbstractLoginStrategy implements ILoginStrategy {

    protected AccountService accountService;

    protected UserService userService;

    protected RandomNicknameService randomNicknameService;

    protected UserHeaderImgService headerImgService;

    //最小密码复杂度
    public static Integer MIX_PASSWORD_COMPLEXITY = 3;

    public AbstractLoginStrategy(AccountService accountService, UserService userService, RandomNicknameService randomNicknameService, UserHeaderImgService headerImgService) {
        this.accountService = accountService;
        this.userService = userService;
        this.randomNicknameService = randomNicknameService;
        this.headerImgService = headerImgService;
    }

    /**
     * 登录前置校验
     *
     * @param request
     * @return
     */
    @Transaction
    public AccountBean preLoginVerify(LoginRequest request) {
        AccountBean accountBean = accountService.queryAccountDetails(QueryAccountRequest.builder()
                .identityType(IdentifierType.subCodeOf(request.getIdentifierType()).getCode())
                .identifier(request.getIdentifier())
                .build());
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
        return accountBean;
    }

    /**
     * 默认用户名密码登录
     *
     * @param userDetailsBean
     * @return
     */
    @Transaction
    public UserDetailsBean processLogin(UserDetailsBean userDetailsBean, LoginRequest request) {
        AccountBean currAccount = userDetailsBean.getCurrAccount();
        //check request password and database password
        String databasePassword = currAccount.getCredential();
        String requestPassword = encryptPassword(request.getCredential());
        if (StringUtils.isEmpty(requestPassword) || Boolean.FALSE.equals(requestPassword.equalsIgnoreCase(databasePassword))) {
            throw new BusinessException(ResultCode.PASSWORD_VALID_FAILED);
        }
        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(currAccount.getUserId()).sections(LoginConstants.QUERY_CURRENT_HEADER_IMG).build());
    }

    /**
     * 注册创建User以及Account
     *
     * @param request
     * @return
     */
    @Transaction
    public UserDetailsBean processRegister(RegisterUserRequest request) {
        //3.创建User
        User newUser = userService.createUser(CreateUserRequest.builder()
                .nickName(randomNicknameService.getRandomNickname()).active(AccountStatus.PRE_ACTIVE.getCode()).build());

        CreateUserHeaderImgRequest headerImg = request.getHeaderImg();
        headerImgService.createUserHeaderImg(CreateUserHeaderImgRequest.builder()
                .userId(newUser.getId())
                .headerImgUrl(Optional.ofNullable(headerImg).map(CreateUserHeaderImgRequest::getHeaderImgUrl).orElse(headerImgService.getDefaultUserHeaderImg()))
                .status(Status.ENABLE.getCode()).build());

        //4. 创建账户并绑定user_id
        accountService.createAccount(CreateAccountRequest.builder()
                .userId(newUser.getId())
                .identityType(IdentifierType.subCodeOf(request.getIdentifierType()).getCode())
                .identifier(request.getIdentifier())
                .credential(encryptPassword(request.getCredential()))
                .verified(Objects.isNull(request.getVerified()) ? Status.DISABLE.getCode() : request.getVerified())
                .status(Objects.isNull(request.getStatus()) ? AccountStatus.PRE_ACTIVE.getCode() : request.getStatus())
                .createDirect(Boolean.TRUE)
                .build());

        return userService.queryUserDetails(QueryUserRequest.builder()
                .id(newUser.getId()).sections(LoginConstants.QUERY_ACCOUNTS).build());
    }

    /**
     * 校验密码是否合法
     * 1.位数限制：6~12位
     * 2.字符种类限制：字母和数字以及特殊字符($,%,@,^,&)混合加密，必须出现任意两个
     *
     * @param password
     * @return
     */
    public int validatePasswdComplexity(String password) {
        int count = 0;
        if (password.length() - password.replaceAll("[A-Z]", "").length() > 0) {
            count++;
        }
        if (password.length() - password.replaceAll("[a-z]", "").length() > 0) {
            count++;
        }
        if (password.length() - password.replaceAll("[0-9]", "").length() > 0) {
            count++;
        }
        if (password.replaceAll("[0-9,A-Z,a-z]", "").length() > 0) {
            count++;
        }
        return count;
    }

    public String encryptPassword(String originalPassword) {
        if (StringUtils.isBlank(originalPassword)) {
            return "";
        }
        return EncryptUtils.SHA256(originalPassword, Constants.USER_PASSWORD_SECRET_KEY);
    }
}
