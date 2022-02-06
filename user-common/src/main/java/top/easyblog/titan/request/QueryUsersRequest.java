package top.easyblog.titan.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author frank.huang
 * @date 2022/02/03 18:17
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryUsersRequest extends PageRequest {

    private List<Long> ids;

    /**
     * 查询选项，accounts、sign_log 分别标识需要查询哪些数据
     */
    private String sections;

}
