package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/01/30 10:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserHeaderImgRequest {
    private Long id;
    private Long userId;
    private Integer status;
}
