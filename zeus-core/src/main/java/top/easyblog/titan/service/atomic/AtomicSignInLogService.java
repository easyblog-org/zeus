package top.easyblog.titan.service.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.SignInLogMapper;
import top.easyblog.titan.dao.auto.model.SignInLog;
import top.easyblog.titan.dao.auto.model.SignInLogExample;
import top.easyblog.titan.dao.custom.mapper.MySignInLogMapper;
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
public class AtomicSignInLogService {

    @Autowired
    private SignInLogMapper signInLogMapper;

    @Autowired
    private MySignInLogMapper mySignInLogMapper;


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
        if (StringUtils.isNotBlank(request.getToken())) {
            criteria.andTokenEqualTo(request.getToken());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        return Iterables.getFirst(signInLogMapper.selectByExample(example), null);
    }

    public List<SignInLog> querySignInLogListByRequest(QuerySignInLogListRequest request) {
        SignInLogExample example = generateExamples(request);
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        example.setOrderByClause(" create_time desc");
        return signInLogMapper.selectByExample(example);
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
        if (Objects.nonNull(request.getAccountId())) {
            criteria.andAccountIdEqualTo(request.getAccountId());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        } else if (CollectionUtils.isNotEmpty(request.getStatuses())) {
            criteria.andStatusIn(request.getStatuses());
        }

        return example;
    }

    public void updateSignInLogByPrimarySelective(UpdateSignInLogRequest request) {
        SignInLog signInLog = new SignInLog();
        BeanUtils.copyProperties(request, signInLog);
        signInLogMapper.updateByPrimaryKeySelective(signInLog);
        log.info("[DB]Update sign_in_log: {}", JsonUtils.toJSONString(request));
    }


    public void updateSignInLogByToken(String token, Integer status) {
        mySignInLogMapper.updateByTokenSelective(token, status);
        log.info("[DB]Update sign_in_log status to [{}] by token [{}]", status, token);
    }
}
