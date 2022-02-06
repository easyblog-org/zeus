package top.easyblog.titan.bean;

import java.util.Date;

import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/01/30 10:31
 */
@Data
public class AccountBean {
    private Long id;

    private Long userId;

    private Integer identityType;

    private String identifier;

    private String credential;

    private Integer verified;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
