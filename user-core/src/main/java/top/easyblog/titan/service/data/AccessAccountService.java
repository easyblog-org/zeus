package top.easyblog.titan.service.data;

import com.google.common.collect.Iterables;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import top.easyblog.titan.dao.auto.mapper.AccountMapper;
import top.easyblog.titan.dao.auto.model.Account;
import top.easyblog.titan.dao.auto.model.AccountExample;
import top.easyblog.titan.request.CreateAccountRequest;
import top.easyblog.titan.request.QueryAccountListRequest;
import top.easyblog.titan.request.QueryAccountRequest;
import top.easyblog.titan.util.JsonUtils;

/**
 * @author frank.huang
 * @date 2022/01/29 16:02
 */
@Slf4j
@Service
public class AccessAccountService {

    @Autowired
    private AccountMapper accountMapper;

    public long insertSelective(CreateAccountRequest request) {
        Account account = new Account();
        BeanUtils.copyProperties(request, account);
        log.info("[DB] Insert account:{}", JsonUtils.toJSONString(account));
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

    public List<Account> queryAccountListByRequest(QueryAccountListRequest request) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        return accountMapper.selectByExample(example);
    }

    public void updateAccountByRequest(Account account) {
        account.setUpdateTime(new Date());
        accountMapper.updateByPrimaryKey(account);
        log.info("[DB] update account[id={}]:{}", account.getId(), JsonUtils.toJSONString(account));
    }

}