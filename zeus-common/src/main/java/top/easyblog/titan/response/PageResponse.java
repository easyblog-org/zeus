package top.easyblog.titan.response;


import java.util.List;

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
public class PageResponse<T> {

    /**
     * 分页大小
     */
    private Integer limit;

    /**
     * 分页偏移量
     */
    private Integer offset;

    /**
     * 总记录个数
     */
    private Long total;

    /**
     * 分页数据
     */
    private List<T> data;

}
