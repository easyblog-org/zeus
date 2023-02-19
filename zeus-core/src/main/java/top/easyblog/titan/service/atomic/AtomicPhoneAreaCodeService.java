package top.easyblog.titan.service.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.PhoneAreaCodeMapper;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;
import top.easyblog.titan.dao.auto.model.PhoneAreaCodeExample;
import top.easyblog.titan.request.CreatePhoneAreaCodeRequest;
import top.easyblog.titan.request.QueryPhoneAreaCodeListRequest;
import top.easyblog.titan.request.QueryPhoneAreaCodeRequest;
import top.easyblog.titan.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:11
 */
@Slf4j
@Service
public class AtomicPhoneAreaCodeService {
    @Autowired
    private PhoneAreaCodeMapper phoneAreaCodeMapper;


    public void insertPhoneAreaCodeByRequest(CreatePhoneAreaCodeRequest request) {
        PhoneAreaCode phoneAreaCode = new PhoneAreaCode();
        phoneAreaCode.setCreateTime(new Date());
        phoneAreaCode.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, phoneAreaCode);
        phoneAreaCodeMapper.insertSelective(phoneAreaCode);
    }

    public PhoneAreaCode queryPhoneAreaCodeByRequest(QueryPhoneAreaCodeRequest request) {
        PhoneAreaCodeExample example = new PhoneAreaCodeExample();
        PhoneAreaCodeExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (StringUtils.isNotBlank(request.getCrownCode())) {
            criteria.andCountryCodeEqualTo(request.getCountryCode());
        }
        if (StringUtils.isNotBlank(request.getCountryCode())) {
            criteria.andCountryCodeEqualTo(request.getCountryCode());
        }
        if (StringUtils.isNotBlank(request.getAreaName())) {
            criteria.andAreaNameEqualTo(request.getAreaName());
        }
        return Iterables.getFirst(phoneAreaCodeMapper.selectByExample(example), null);
    }


    public List<PhoneAreaCode> queryPhoneAreaCodeListByRequest(QueryPhoneAreaCodeListRequest request) {
        PhoneAreaCodeExample example = generatePhoneAreaCodeExamples(request);
        return phoneAreaCodeMapper.selectByExample(example);
    }

    public long countByRequest(QueryPhoneAreaCodeListRequest request) {
        return phoneAreaCodeMapper.countByExample(generatePhoneAreaCodeExamples(request));
    }

    private PhoneAreaCodeExample generatePhoneAreaCodeExamples(QueryPhoneAreaCodeListRequest request) {
        PhoneAreaCodeExample example = new PhoneAreaCodeExample();
        PhoneAreaCodeExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }
        if (StringUtils.isNotBlank(request.getAreaName())) {
            criteria.andAreaNameLike("%" + request.getAreaName() + "%");
        }
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }

        return example;
    }

    public void updatePhoneAreaCodeByRequest(PhoneAreaCode areaCode) {
        areaCode.setUpdateTime(new Date());
        phoneAreaCodeMapper.updateByPrimaryKeySelective(areaCode);
        log.info("[DB] update area code:{}", JsonUtils.toJSONString(areaCode));
    }
}
