package top.easyblog.titan.request;

import lombok.*;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2022-02-10 22:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueryPhoneAreaCodeListRequest extends PageRequest {
    private List<Long> ids;
    private String areaName;
}
