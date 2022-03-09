package top.easyblog.titan.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/02/02 09:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueryUserHeaderImgsRequest extends PageRequest {
    private Long id;
    private List<Long> ids;
    private Long userId;
    private List<Long> userIds;
    private Integer status;
}
