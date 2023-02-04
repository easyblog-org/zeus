package top.easyblog.titan.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author: frank.huang
 * @date: 2022-01-29 20:15
 */
public final class LoginConstants {

    //====================================Redis======================================
    /**
     * 用户登录 token 最大过期时间: 15天
     */
    public final static Long LOGIN_TOKEN_MAX_EXPIRE = 60 * 60 * 24 * 15L;
    /**
     * 用户登录token
     */
    public final static String LOGIN_TOKEN_KEY_PREFIX = "account:type:%s:identifier:%s";

    public final static String USER_INFO_PREFIX = "user:token:%s";

    // 手机验证码key
    public static final String PHONE_LOGIN_CAPTCHA_CODE = "zeus:phone_captcha_code:%s";

    // 手机验证码有效时间(分钟)
    public static final long PHONE_LOGIN_CAPTCHA_CODE_EXPIRE = 5;

    //====================================Business======================================
    /**
     * 密码最小长度
     */
    public final static Integer PASSWORD_MIN_LEN = 6;
    /**
     * 密码最大长度
     */
    public final static Integer PASSWORD_MAX_LEN = 6;
    /**
     * 密码可以包含的特殊字符
     */
    public final static Set<String> PASSWORD_SPECIAL_CHARACTERS = Sets.newHashSet("$", "%", "@", "^", "&");
    /**
     * 查询头像section
     */
    public static final String QUERY_HEADER_IMG = "header_img";
    /**
     * 当前头像
     */
    public static final String QUERY_CURRENT_HEADER_IMG = "current_header_img";
    /**
     * 查询用户所有账户信息section
     */
    public static final String QUERY_ACCOUNTS = "accounts";
    /**
     * 查询用户登录情况section
     */
    public static final String QUERY_SIGN_LOG = "sign_log";


    public static final String COMMON_GRANT_TYPE = "authorization_code";

    /**
     * 验证码默认长度
     */
    public static final int DEFAULT_CAPTCHA_CODE_LENGTH = 6;
}
