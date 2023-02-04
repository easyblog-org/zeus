package top.easyblog.titan.feign.client.dto;

import lombok.Data;

/**
 * @author: frank.huang
 * @date: 2022-03-06 12:43
 */
@Data
public class GiteeAuthDTO {
    private String id;
    private String login;
    private String avatarUrl;
}
