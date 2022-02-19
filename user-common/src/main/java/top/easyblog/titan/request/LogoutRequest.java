package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: frank.huang
 * @date: 2022-02-13 20:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    /**
     *
     */
    @NotNull(message = "Required parameter `user_id` is not present")
    private Long userId;

    /**
     * 登录账户类型
     */
    @NotNull(message = "Required parameter `identifier_type` is not present")
    private Byte identifierType;

    /**
     * 登录账号
     */
    @NotBlank(message = "Required parameter `identifier` is not present")
    private String identifier;
}
