package top.easyblog.titan.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Example of request parameter bean to query an object
 *
 * @author: frank.huang
 * @date: 2021-11-01 20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryUserRequest {

    private String name;
    private String address;
    private Integer age;

}
