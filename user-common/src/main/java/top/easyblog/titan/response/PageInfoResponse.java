package top.easyblog.titan.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Paging response parameters
 *
 * @author huangxin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoResponse<T> {

    /**
     * 总记录个数
     */
    private Integer total;

    /**
     * 分页大小
     */
    private Integer limit;

    /**
     * 分页偏移量
     */
    private Integer offset;

    /**
     * 此分页数据量
     */
    private Integer amount;
    /**
     * 分页数据
     */
    private T data;

}
