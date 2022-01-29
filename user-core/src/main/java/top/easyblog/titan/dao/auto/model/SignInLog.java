package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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