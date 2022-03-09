package top.easyblog.titan.request;

import lombok.Data;

/**
 * 分页基本参数
 *
 * @author frank.huang
 * @date 2022/02/03 18:38
 */
@Data
public abstract class PageRequest {
    protected Integer offset;
    protected Integer limit;
}
