package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserHeaderImg {
    private Long id;

    private String headerImgUrl;

    private Long userId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}