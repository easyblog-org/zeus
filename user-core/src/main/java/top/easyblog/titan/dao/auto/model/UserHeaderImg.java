package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHeaderImg {
    private Long id;

    private String headerImgUrl;

    private Long userId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}