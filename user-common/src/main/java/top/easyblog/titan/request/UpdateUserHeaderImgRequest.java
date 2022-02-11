package top.easyblog.titan.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/11 15:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserHeaderImgRequest {
    private Long id;

    private String headerImgUrl;

    private Long userId;

    private Integer status;

    private Date updateTime;
}
