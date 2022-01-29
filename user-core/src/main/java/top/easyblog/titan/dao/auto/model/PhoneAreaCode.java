package top.easyblog.titan.dao.auto.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAreaCode {
    private Long id;

    private String crownCode;

    private String countryCode;

    private String areaCode;

    private String areaName;

    private Date createTime;

    private Date updateTime;
}