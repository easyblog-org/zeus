package top.easyblog.titan.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.RolesBean;
import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.bean.UserHeaderImgBean;

import java.util.List;
import java.util.Map;

/**
 * @author: frank.huang
 * @date: 2023-02-24 22:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserSectionContext {
    // 当前头像
    private Map<Long, UserHeaderImgBean> userCurrentImagesMap;
    // 历史头像
    private Map<Long, List<UserHeaderImgBean>> userHistoryImagesMap;
    // 账户
    private Map<Long, List<AccountBean>> accountsMap;
    // 角色
    private Map<Long, List<RolesBean>> rolesMap;
    // 登录记录
    private Map<Long, List<SignInLogBean>> signInLogsMap;
}
