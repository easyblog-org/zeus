package top.easyblog.titan.request;

import lombok.Data;

/**
 * @author frank.huang
 * @date 2022/02/06 09:45
 */
@Data
public class QueryAccountListRequest {
    private Long userId;
    private Integer status;
}
