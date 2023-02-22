package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-22 20:14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRolesRequest {

    private Boolean enabled;

    private Integer userId;

    private Integer roleId;
}
