package top.easyblog.titan.constant;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author: frank.huang
 * @date: 2022-01-29 20:15
 */
public final class LoginConstants {
    public final static Long LOG_IN_EXPIRE = 60 * 60 * 24 * 15L;
    public final static Integer PASSWORD_MIN_LEN = 6;
    public final static Integer PASSWORD_MAX_LEN = 6;
    public final static Set<String> PASSWORD_SPECIAL_CHARACTERS = Sets.newHashSet("$", "%", "@", "^", "&");
}
