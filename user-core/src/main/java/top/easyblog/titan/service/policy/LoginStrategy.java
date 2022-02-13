package top.easyblog.titan.service.policy;

import org.apache.commons.lang3.StringUtils;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.constant.LoginConstants;
import top.easyblog.titan.request.LoginRequest;
import top.easyblog.titan.request.RegisterUserRequest;
import top.easyblog.titan.util.EncryptUtils;

/**
 * @author frank.huang
 * @date 2022/01/29 16:19
 */
public interface LoginStrategy {

    /**
     * 登录策略
     *
     * @param request
     * @return
     */
    UserDetailsBean doLogin(LoginRequest request);

    /**
     * 注册策略
     *
     * @param request
     * @return
     */
    UserDetailsBean doRegister(RegisterUserRequest request);

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
        int[] strength = new int[3];   //密码强度
        int specialFlag = 2, letterFlag = 1, numberFlag = 0;
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

    default String encryptPassword(String originalPassword) {
        return EncryptUtils.SHA256(originalPassword, Constants.USER_PASSWORD_SECRET_KEY);
    }
}
