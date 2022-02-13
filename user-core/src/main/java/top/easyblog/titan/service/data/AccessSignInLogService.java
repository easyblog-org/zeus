package top.easyblog.titan.service.data;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.SignInLogMapper;
import top.easyblog.titan.dao.auto.model.SignInLog;
import top.easyblog.titan.dao.auto.model.SignInLogExample;
import top.easyblog.titan.request.CreateSignInLogRequest;
import top.easyblog.titan.request.QuerySignInLogListRequest;
import top.easyblog.titan.request.QuerySignInLogRequest;
import top.easyblog.titan.request.UpdateSignInLogRequest;
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
public class AccessSignInLogService {
    @Autowired
    private SignInLogMapper signInLogMapper;


    public SignInLog insertSignInLogByRequest(CreateSignInLogRequest request) {
        SignInLog signInLog = new SignInLog();
        signInLog.setCreateTime(new Date());
        signInLog.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, signInLog);
        signInLogMapper.insertSelective(signInLog);
        log.info("[DB] insert new sign in log:{}", JsonUtils.toJSONString(signInLog));
        return signInLog;
    }

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
        return signInLogMapper.selectByExample(generateExamples(request));
    }

    public long countByRequest(QuerySignInLogListRequest request) {
        return signInLogMapper.countByExample(generateExamples(request));
    }

    private SignInLogExample generateExamples(QuerySignInLogListRequest request) {
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
        return example;
    }

    public void updateSignInLogByRequestSelective(UpdateSignInLogRequest request) {
        SignInLog signInLog = new SignInLog();
        BeanUtils.copyProperties(request, signInLog);
        signInLogMapper.updateByPrimaryKeySelective(signInLog);
    }

}
