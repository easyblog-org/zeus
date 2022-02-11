package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.Data;

@Data
public class SignInLog {
    private Long id;

    private Long userId;

    private Integer status;

    private String ip;

    private String device;

    private String operationSystem;

    private String location;

    private Date createTime;

    private Date updateTime;
}