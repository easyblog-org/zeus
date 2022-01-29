package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAuth {
    private Long id;

    private Integer phoneAreaCode;

    private String phone;

    private Date createTime;

    private Date updateTime;

}