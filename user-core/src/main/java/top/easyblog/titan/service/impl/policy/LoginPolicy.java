package top.easyblog.titan.service.impl.policy;

import org.apache.commons.lang3.StringUtils;
import top.easyblog.titan.bean.LoginDetailsBean;
import top.easyblog.titan.bean.RegisterDetailsBean;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:19
 */
public interface LoginPolicy {
    /**
     * 登录策略
     *
     * @param request
     * @return
     */
    LoginDetailsBean doLogin(LoginRequest request);

    /**
     * 注册策略
     *
     * @param request
     * @return
     */
    RegisterDetailsBean doRegister(RegisterUserRequest request);

    /**
     * 校验密码是否合法
     * 1.位数限制：6~12位
     * 2.字符种类限制：字母和数字以及特殊字符($,%,@,^,&)混合加密，必须出现任意两个
     *
     * @param password
     * @return
     */
    default boolean checkPasswordValid(String password) {
        if (!StringUtils.isNotBlank(password)) {
            return false;
        }
        int len = 0;
        if ((len = password.length()) < LoginConstants.PASSWORD_MIN_LEN || len > LoginConstants.PASSWORD_MAX_LEN) {
            return false;
        }
        char[] chars = password.toCharArray();
        int[] strength = new int[3];
        int specialFlag = 0, letterFlag = 1, numberFlag = 2;
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
}
