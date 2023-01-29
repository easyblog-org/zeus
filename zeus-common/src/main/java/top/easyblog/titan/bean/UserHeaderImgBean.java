package top.easyblog.titan.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/01/30 10:32
 */
@Data
public class UserHeaderImgBean {
    private Long id;

    private String headerImgUrl;

    private Long userId;

    private Boolean isCurrentHeader;

    private Date createTime;

    private Date updateTime;
}
