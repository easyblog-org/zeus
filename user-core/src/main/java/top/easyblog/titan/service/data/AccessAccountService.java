package top.easyblog.titan.service.data;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.AccountMapper;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.request.CreateAccountRequest;

import java.util.Date;

/**
 * @author frank.huang
 * @date 2022/01/29 16:02
 */
@Service
public class AccessAccountService {

    @Autowired
    private AccountMapper accountMapper;

    public long insertSelective(CreateAccountRequest request) {
        Account account = new Account();
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, account);
        return accountMapper.insertSelective(account);
    }

}
