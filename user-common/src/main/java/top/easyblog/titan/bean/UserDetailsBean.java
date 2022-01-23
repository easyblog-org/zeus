package top.easyblog.titan.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Demo Bean
 * @author: frank.huang
 * @date: 2021-11-01 21:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsBean {

    private String name;
    private String address;
    private Integer age;

}
