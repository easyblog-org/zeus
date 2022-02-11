package top.easyblog.titan.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/11 17:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSignInLogRequest {
    private Long id;

    private Long userId;

    private Integer status;

    private String ip;

    private String device;

    private String operationSystem;

    private String location;

    private Date updateTime;
}
