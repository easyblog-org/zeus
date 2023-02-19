package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author: frank.huang
 * @date: 2023-02-19 16:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRolesRequest {
    /**
     * 角色唯一标识
     */
    private String code;
    /**
     * 角色名称
     */
    @NotBlank(message = "Required param 'name' is not present.")
    private String name;
    /**
     * 是否启用
     */
    @Builder.Default
    private Boolean enabled = true;
    /**
     * 角色描述
     */
    private String description;
}
