package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: frank.huang
 * @date: 2023-02-19 16:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRolesRequest {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 删除启用
     */
    private Boolean enabled;
    /**
     * 角色描述
     */
    private String description;
}
