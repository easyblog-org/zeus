package top.easyblog.titan.service.data;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import top.easyblog.titan.dao.auto.mapper.SignInLogMapper;
import top.easyblog.titan.dao.auto.model.SignInLog;
import top.easyblog.titan.dao.auto.model.SignInLogExample;
import top.easyblog.titan.request.QuerySignInLogListRequest;
import top.easyblog.titan.request.QuerySignInLogRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:11
 */
@Service
public class AccessSignInLogService {
    @Autowired
    private SignInLogMapper signInLogMapper;


    public SignInLog querySignLogByRequest(QuerySignInLogRequest request) {
        SignInLogExample example = new SignInLogExample();
        SignInLogExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        return Iterables.getFirst(signInLogMapper.selectByExample(example), null);
    }

    public List<SignInLog> querySignInLogListByRequest(QuerySignInLogListRequest request) {
        SignInLogExample example = new SignInLogExample();
        SignInLogExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        } else if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        } else if (CollectionUtils.isNotEmpty(request.getUserIds())) {
            criteria.andUserIdIn(request.getUserIds());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        } else if (CollectionUtils.isNotEmpty(request.getStatuses())) {
            criteria.andStatusIn(request.getStatuses());
        }

        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        return signInLogMapper.selectByExample(example);
    }

}
