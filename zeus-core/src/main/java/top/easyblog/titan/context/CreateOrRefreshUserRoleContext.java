package top.easyblog.titan.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-22 20:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrRefreshUserRoleContext {
    private Long userId;
    private List<String> roles;
}
