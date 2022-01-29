package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注册请求参数
 *
 * @author: frank.huang
 * @date: 2021-11-01 20:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
    /**
     * 账号类型
     */
    private Byte identifierType;
    /**
     * 账号
     */
    private String identifier;
    /**
     * 账号密码
     */
    private String credential;
    /**
     * 账号密码验证
     */
    private String credentialAgain;
}
