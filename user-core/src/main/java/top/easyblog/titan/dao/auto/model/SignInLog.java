package top.easyblog.titan.dao.auto.model;

import lombok.Data;

import java.util.Date;

@Data
public class SignInLog {
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