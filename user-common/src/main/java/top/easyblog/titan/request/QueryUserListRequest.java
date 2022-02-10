package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author frank.huang
 * @date 2022/02/03 18:17
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryUserListRequest extends PageRequest {

    private List<Long> ids;

    private Integer status;

    private Integer level;

    private String nickname;

    /**
     * 查询选项，accounts、sign_log 分别标识需要查询哪些数据
     */
    private String sections;

}
