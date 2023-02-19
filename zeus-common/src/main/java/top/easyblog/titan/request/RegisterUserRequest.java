package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
     * 昵称
     */
    private String nickname;
    /**
     * 账号类型
     */
    @NotNull(message = "Required parameter `identifier_type` is not present")
    private Integer identifierType;
    /**
     * 账号
     */
    @NotBlank(message = "Required parameter `identifier` is not present")
    private String identifier;
    /**
     * 账号密码 或者 token
     */
    @NotBlank(message = "Required parameter `credential` is not present")
    private String credential;
    /**
     * 账号密码验证
     */
    private String credentialAgain;
    /**
     * 账户状态
     */
    private Integer status;
    /**
     * 是否验证
     */
    private Integer verified;
    /**
     * 头像
     */
    private CreateUserHeaderImgRequest headerImg;
}
