package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.Data;

@Data
public class PhoneAuth {
    private Long id;

    private Integer phoneAreaCode;

    private String phone;

    private Date createTime;

    private Date updateTime;
}