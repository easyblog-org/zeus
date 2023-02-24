package top.easyblog.titan.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author frank.huang
 * @date 2022/01/30 14:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuerySignInLogListRequest {
    private Long id;

    private List<Long> ids;

    private Long userId;

    private List<Long> userIds;

    private Integer status;

    private List<Integer> statuses;

    @Builder.Default
    private Integer offset = 0;

    @Builder.Default
    private Integer limit = 10;
}
