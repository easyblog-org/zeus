package top.easyblog.titan.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * System unified response code
 *
 * @author frank.huang
 * @since 2021/8/21 22:13
 */
@Getter
@AllArgsConstructor
public enum ZeusResultCode {
    //sever internal
    SUCCESS,
    FAIL,
    INVALID_PARAMS,
    NOT_FOUND,
    INTERNAL_ERROR,
    DATA_ACCESS_FAIL,
    ERROR_LOGIN_POLICY,
    ERROR_OAUTH_POLICY,
    ERROR_CAPTCHA_CODE_SEND_STRATEGY,

    //parameter
    INVALID_CONTINENT_TYPE,
    PARAMTER_NOT_VALID,
    PARAMETER_VALIDATE_FAILED,
    LOGIN_FAILED,
    LOGOUT_FAILED,
    REQUIRED_PARAM_TOKEN_NOT_EXISTS,
    REQUIRED_REQUEST_PARAM_NOT_EXISTS,
    REPEAT_USER_NAME,
    PASSWORD_NOT_VALID,
    PASSWORD_NOT_EQUAL,
    PASSWORD_VALID_FAILED,
    USER_NOT_FOUND,
    USER_ACCOUNT_EXISTS,
    USER_HEADER_IMG_NOT_FOUND,
    USER_HEADER_IMGS_NOT_FOUND,
    ACCOUNT_NOT_FOUND,
    ACCOUNT_IS_FREEZE,
    ACCOUNT_IS_DELETE,
    ACCOUNT_IS_PRE_ACTIVE,
    SIGN_IN_LOG_NOT_FOUND,
    PHONE_AREA_CODE_NOT_FOUND,
    PHONE_AREA_CODE_ALREADY_EXISTS,
    PHONE_ACCOUNT_ALREADY_EXISTS,
    IDENTIFIER_NOT_EMAIL,
    EMAIL_ACCOUNT_EXISTS,
    IDENTIFIER_NOT_PHONE,
    INVALID_IDENTITY_TYPE,
    AUTH_TOKEN_NOT_FOUND,
    DELETE_OPERATION_NOT_PERMISSION,

    SIGN_FAIL,
    SIGN_ERROR,
    SIGN_NOT_FOUND,
    SING_HAS_EXPIRE,


    //三方登录
    REQUEST_GITHUB_ACCESS_TOKEN_FAILED,  //请求Github access token失败
    REQUEST_GITHUB_USER_INFO_FAILED,     //请求Github用户信息失败
    REQUEST_GITEE_ACCESS_TOKEN_FAILED,   //请求Gitee access token失败
    REQUEST_GITEE_USER_INFO_FAILED,      //请求Gitee用户信息失败

    REMOTE_INVOKE_FAILED,   //远程调用失败
    SEND_CAPTCHA_FAILED,   // 发送验证码失败
    INCORRECT_OR_EXPIRE_CAPTCHA,    //不正确或已过期的验证码

    DB_OPERATE_RECORD_NOT_ALLOW_NULL, // 数据库操作对象不能为空
    ROLE_EXISTS,  //角色已经存在
    ROLE_NOT_FOUND,  //角色未找到
    ;


    public String getCode() {
        return this.name().toLowerCase();
    }
}
