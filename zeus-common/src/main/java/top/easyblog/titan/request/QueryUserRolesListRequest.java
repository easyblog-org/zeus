package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryUserRolesListRequest {
    private List<Integer> userIds;
    private List<Integer> rolesIds;
    private Boolean enabled;
    @Builder.Default
    private Integer limit = 10;
    @Builder.Default
    private Integer offset = 0;
}
