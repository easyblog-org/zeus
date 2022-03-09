package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @NotNull(message = "Required parameter `user_id` is not present")
    private Long userId;

    private Integer status;

    private Date updateTime;
}
