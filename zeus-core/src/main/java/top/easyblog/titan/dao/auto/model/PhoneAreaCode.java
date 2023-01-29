package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.Data;

@Data
public class PhoneAreaCode {
    private Long id;

    private String crownCode;

    private String countryCode;

    private String areaCode;

    private String areaName;

    private Date createTime;

    private Date updateTime;
}