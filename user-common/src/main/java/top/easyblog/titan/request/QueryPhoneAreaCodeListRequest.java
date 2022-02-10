package top.easyblog.titan.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2022-02-10 22:08
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class QueryPhoneAreaCodeListRequest extends PageRequest {
    private List<Long> ids;
}
