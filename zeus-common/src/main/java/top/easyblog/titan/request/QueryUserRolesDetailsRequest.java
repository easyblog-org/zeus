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
public class QueryUserRolesDetailsRequest {
    private Long userId;
    private Long roleId;
    @Builder.Default
    private Boolean enabled = true;
}
