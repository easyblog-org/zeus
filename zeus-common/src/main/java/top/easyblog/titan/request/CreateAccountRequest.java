package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-01-29 22:13
 */
@Data
@Builder
public class CreateAccountRequest {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 账号类型
     *
     * @see top.easyblog.titan.enums.IdentifierType
     */
    private Integer identityType;
    /**
     *  账号
     */
    private String identifier;
    /**
     * 站内账号是密码、第三方登录是Token
     */
    private String credential;
    /**
     * 授权账号是否被验证(手机号验证码通过、邮箱验证...)
     */
    private Integer verified;
    /**
     * 状态状态
     */
    private Integer status;
    /**
     * 是否直接创建
     */
    private Boolean createDirect;
}
