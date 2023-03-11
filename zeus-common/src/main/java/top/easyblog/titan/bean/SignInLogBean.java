package top.easyblog.titan.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/01/30 10:35
 */
@Data
public class SignInLogBean {
    private Long id;

    private Long userId;

    private Long accountId;

    private String token;

    private Integer status;

    private String ip;

    private String device;

    private String operationSystem;

    private String location;

    private Date createTime;

    private Date updateTime;
}
