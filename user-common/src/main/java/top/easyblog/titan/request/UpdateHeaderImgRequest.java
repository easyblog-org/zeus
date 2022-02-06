package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/02 09:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHeaderImgRequest {
    private Long id;
    private Long userId;
    private Integer status;
    private String headerImgUrl;
}
