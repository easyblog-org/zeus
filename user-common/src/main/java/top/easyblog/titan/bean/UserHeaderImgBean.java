package top.easyblog.titan.bean;

import java.util.Date;

import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/01/30 10:32
 */
@Data
public class UserHeaderImgBean {
    private Long id;

    private String headerImgUrl;

    private Long userId;
    
    private boolean currentHeader;

    private Date createTime;

    private Date updateTime;
}
