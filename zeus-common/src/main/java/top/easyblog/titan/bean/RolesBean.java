package top.easyblog.titan.bean;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2023-02-19 16:36
 */
@Data
public class RolesBean {
    private Long id;

    private String code;

    private String name;

    private String description;

    private Boolean enabled;

    private Long createTime;

    private Long updateTime;
}
