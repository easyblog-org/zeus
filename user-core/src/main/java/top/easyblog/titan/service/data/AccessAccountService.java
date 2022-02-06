package top.easyblog.titan.service.data;

import com.google.common.collect.Iterables;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

import top.easyblog.titan.dao.auto.mapper.AccountMapper;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.dao.auto.model.AccountExample;
import top.easyblog.titan.request.CreateAccountRequest;
import top.easyblog.titan.request.QueryAccountRequest;

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


    public Account queryAccountByRequest(QueryAccountRequest request) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getId());
        }
        if (Objects.nonNull(request.getIdentityType())) {
            criteria.andIdentityTypeEqualTo(request.getIdentityType());
        }
        if (StringUtils.isNotBlank(request.getIdentifier())) {
            criteria.andIdentifierEqualTo(request.getIdentifier());
        }
        if (StringUtils.isNotBlank(request.getCredential())) {
            criteria.andCredentialEqualTo(request.getCredential());
        }
        return Iterables.getFirst(accountMapper.selectByExample(example), null);
    }

}
