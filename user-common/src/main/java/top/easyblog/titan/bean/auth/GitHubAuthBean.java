package top.easyblog.titan.bean.auth;

import lombok.Data;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/02/21 16:19
 */
@Data
public class GitHubAuthBean {
    /**
     * 用户名
     */
    private String login;
    /**
     * GitHub openId, 可以和系统用户id绑定
     */
    private String id;
    /**
     * node_id
     */
    private String nodeId;
    /**
     * 用户的GitHub头像URL
     */
    private String avatarUrl;
    /**
     * 用户创建时间
     */
    private Date createTime;
}
