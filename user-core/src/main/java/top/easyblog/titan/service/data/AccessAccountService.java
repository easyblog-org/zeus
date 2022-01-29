package top.easyblog.titan.service.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.easyblog.titan.dao.auto.mapper.AccountMapper;

/**
 * @author frank.huang
 * @date 2022/01/29 16:02
 */
@Service
public class AccessAccountService {

    @Autowired
    private AccountMapper accountMapper;


}
