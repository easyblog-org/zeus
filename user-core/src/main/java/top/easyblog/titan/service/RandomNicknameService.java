package top.easyblog.titan.service;

import org.springframework.stereotype.Service;
import top.easyblog.titan.util.RandomNicknameUtils;

/**
 * @author: frank.huang
 * @date: 2022-02-19 15:07
 */
@Service
public class RandomNicknameService {


    public String getRandomNickname() {
        return RandomNicknameUtils.generateNickname();
    }

}
