package top.easyblog.titan.dao.auto.model;

import lombok.Data;

import java.util.Date;

@Data
public class PhoneAuth {
    private Long id;

    private String phoneAreaCode;

    private String phone;

    private Date createTime;

    private Date updateTime;
}