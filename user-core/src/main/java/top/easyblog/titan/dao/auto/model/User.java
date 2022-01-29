package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String nickName;

    private Integer integration;

    private Integer headerImgId;

    private Integer level;

    private Integer visit;

    private Integer active;

    private Date createTime;

    private Date updateTime;
}