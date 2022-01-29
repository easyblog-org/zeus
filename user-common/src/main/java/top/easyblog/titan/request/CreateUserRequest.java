package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2022-01-29 21:41
 */
@Data
@Builder
public class CreateUserRequest {
    private String nickName;
    private Integer integration;
    private Integer headerImgId;
    private Integer level;
    private Integer visit;
    private Integer active;
    private Date createTime;
    private Date updateTime;
}
