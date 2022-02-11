package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/06 09:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryAccountListRequest {
    private Long userId;
    private Integer status;
}
