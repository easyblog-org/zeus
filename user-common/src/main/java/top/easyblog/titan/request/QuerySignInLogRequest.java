package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/01/30 13:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuerySignInLogRequest {
    private Long id;

    private Long userId;

    private Integer status;
}
