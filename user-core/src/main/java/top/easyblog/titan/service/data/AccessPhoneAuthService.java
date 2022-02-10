package top.easyblog.titan.service.data;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.PhoneAuthMapper;
import top.easyblog.titan.dao.auto.model.PhoneAuth;
import top.easyblog.titan.dao.auto.model.PhoneAuthExample;
import top.easyblog.titan.request.CreatePhoneAuthRequest;
import top.easyblog.titan.request.QueryPhoneAuthRequest;
import top.easyblog.titan.util.JsonUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:13
 */
@Slf4j
@Service
public class AccessPhoneAuthService {
    @Autowired
    private PhoneAuthMapper phoneAuthMapper;


    public void insertByRequestSelective(CreatePhoneAuthRequest request) {
        PhoneAuth phoneAuth = new PhoneAuth();
        phoneAuth.setCreateTime(new Date());
        phoneAuth.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, phoneAuth);
        phoneAuthMapper.insertSelective(phoneAuth);
        log.info("[DB] insert new phone auth account:{}", JsonUtils.toJSONString(phoneAuth));
    }


    public PhoneAuth queryPhoneAuthByRequest(QueryPhoneAuthRequest request) {
        PhoneAuthExample example = new PhoneAuthExample();
        PhoneAuthExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (Objects.nonNull(request.getPhoneAreaCode())) {
            criteria.andPhoneAreaCodeEqualTo(request.getPhoneAreaCode());
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            criteria.andPhoneEqualTo(request.getPhone());
        }
        return Iterables.getFirst(phoneAuthMapper.selectByExample(example), null);
    }

    public void updatePhoneAuthByRequest(PhoneAuth phoneAuth) {
        phoneAuth.setUpdateTime(new Date());
        phoneAuthMapper.updateByPrimaryKey(phoneAuth);
        log.info("[DB] update phone auth account:{}", JsonUtils.toJSONString(phoneAuth));
    }

}
