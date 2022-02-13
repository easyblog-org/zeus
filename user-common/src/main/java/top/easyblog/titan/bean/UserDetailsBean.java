package top.easyblog.titan.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Demo Bean
 *
 * @author: frank.huang
 * @date: 2021-11-01 21:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsBean {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 积分
     */
    private Integer integration;
    /**
     * 头像
     */
    private List<UserHeaderImgBean> userHeaderImg;

    /**
     * 当前账户
     */
    private AccountBean currAccount;

    /**
     * 账号列表
     */
    private List<AccountBean> accounts;

    /**
     * 当前登录设备列表
     */
    private List<SignInLogBean> signInLogs;
    /**
     * 用户等级
     */
    private Integer level;
    /**
     * 用户（文章）访问量
     */
    private Integer visit;
    /**
     * 用户账号状态，和account状态关联
     */
    private Integer active;
    /**
     * 用户注册时间
     */
    private Date createTime;
    /**
     * 最近更新时间
     */
    private Date updateTime;
}
