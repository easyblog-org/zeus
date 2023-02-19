package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryRolesDetailsRequest {
    private Long id;
    private String code;
    private String name;
}
