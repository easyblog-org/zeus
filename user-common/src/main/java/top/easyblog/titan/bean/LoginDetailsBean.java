package top.easyblog.titan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户登录详情
 *
 * @author frank.huang
 * @date 2022/01/29 15:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetailsBean extends AuthenticationDetailsBean {
    /**
     * 登录获得服务器token
     */
    private String token;
}
