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
public enum ResultCode {
    //sever internal
    SUCCESS,
    INVALID_PARAMS,
    NOT_FOUND,
    INTERNAL_ERROR,
    DATA_ACCESS_FAIL,


    //parameter
    PARAMETER_VALIDATE_FAILED,
    LOGIN_FAILED,
    REQUIRED_PARAM_TOKEN_NOT_EXISTS,
    REQUIRED_REQUEST_PARAM_NOT_EXISTS,
    REPEAT_USER_NAME,
    PASSWORD_NOT_VALID,
    PASSWORD_NOT_EQUAL,
    USER_NOT_FOUND,
    USER_ACCOUNT_EXISTS,
    USER_HEADER_IMG_NOT_FOUND,
    USER_HEADER_IMGS_NOT_FOUND,
    USER_ACCOUNT_NOT_FOUND,
    SIGN_IN_LOG_NOT_FOUND,
    PHONE_AREA_CODE_NOT_FOUND,
    PHONE_ACCOUNT_ALREADY_EXISTS,

    SIGN_FAIL,
    SIGN_ERROR,
    SIGN_NOT_FOUND,
    SING_HAS_EXPIRE,

    REMOTE_INVOKE_FAIL;


    public String getCode() {
        return this.name().toLowerCase();
    }
}
