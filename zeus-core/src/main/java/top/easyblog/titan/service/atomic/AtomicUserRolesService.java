package top.easyblog.titan.service.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.DBQueryParamNonNull;
import top.easyblog.titan.dao.auto.mapper.UserRolesMapper;
import top.easyblog.titan.dao.auto.model.UserRoles;
import top.easyblog.titan.dao.auto.model.UserRolesExample;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.QueryUserRolesDetailsRequest;
import top.easyblog.titan.request.QueryUserRolesListRequest;
import top.easyblog.titan.request.UpdateUserRolesRequest;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.util.JsonUtils;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-19 15:07
 */
@Slf4j
@Service
public class AtomicUserRolesService {

    @Autowired
    private UserRolesMapper mapper;


    public UserRoles insertOne(UserRoles record) {
        if (Objects.isNull(record)) {
            throw new BusinessException(ZeusResultCode.DB_OPERATE_RECORD_NOT_ALLOW_NULL);
        }
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        mapper.insertSelective(record);
        log.info("[DB]Insert new user_role successfully!Details==>{}", JsonUtils.toJSONString(record));
        return record;
    }


    public void updateByPrimaryKey(UserRoles record) {
        if (Objects.isNull(record)) {
            throw new BusinessException(ZeusResultCode.DB_OPERATE_RECORD_NOT_ALLOW_NULL);
        }
        record.setUpdateTime(new Date());
        mapper.updateByPrimaryKey(record);
        log.info("[DB]Update user_role successfully!Details==>{}", JsonUtils.toJSONString(record));
    }


    @DBQueryParamNonNull
    public UserRoles queryDetails(@NotEmpty QueryUserRolesDetailsRequest request) {
        UserRolesExample example = new UserRolesExample();
        UserRolesExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getRoleId())) {
            criteria.andRoleIdEqualTo(request.getRoleId());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        return Iterables.getFirst(mapper.selectByExample(example), null);
    }

    public List<UserRoles> queryList(QueryUserRolesListRequest request) {
        UserRolesExample example = generateExamples(request);
        example.setLimit(request.getLimit());
        example.setOffset(request.getOffset());
        return mapper.selectByExample(example);
    }

    public long countByRequest(QueryUserRolesListRequest request) {
        UserRolesExample example = generateExamples(request);
        return mapper.countByExample(example);
    }


    private UserRolesExample generateExamples(QueryUserRolesListRequest request) {
        UserRolesExample example = new UserRolesExample();
        UserRolesExample.Criteria criteria = example.createCriteria();
        if (CollectionUtils.isNotEmpty(request.getUserIds())) {
            criteria.andUserIdIn(request.getUserIds());
        }
        if (CollectionUtils.isNotEmpty(request.getRolesIds())) {
            criteria.andRoleIdIn(request.getRolesIds());
        }
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        return example;
    }

    public void updateByExampleSelective(UserRoles userRoles, UpdateUserRolesRequest request) {
        UserRolesExample example = new UserRolesExample();
        UserRolesExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getRoleId())) {
            criteria.andRoleIdEqualTo(request.getRoleId());
        }
        mapper.updateByExampleSelective(userRoles, example);
    }

    public void deleteByExample(UpdateUserRolesRequest request) {
        UserRolesExample example = new UserRolesExample();
        UserRolesExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getEnabled())) {
            criteria.andEnabledEqualTo(request.getEnabled());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getRoleId())) {
            criteria.andRoleIdEqualTo(request.getRoleId());
        }
        mapper.deleteByExample(example);
    }
}
