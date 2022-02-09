package top.easyblog.titan.request;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/01/30 10:48
 */
@Data
@Builder
public class UpdateUserRequest implements BaseRequest {
    private Long id;

    private String nickName;

    private Integer integration;

    private Integer headerImgId;

    private Integer level;

    private Integer visit;

    private Integer active;

    private Date updateTime;
}
