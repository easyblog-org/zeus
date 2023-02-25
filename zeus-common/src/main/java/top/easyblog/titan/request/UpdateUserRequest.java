package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author frank.huang
 * @date 2022/01/30 10:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest implements BaseRequest {
    private String nickName;

    private Integer integration;

    private Integer level;

    private Integer visit;

    private Integer active;

    private List<String> roles;
}
