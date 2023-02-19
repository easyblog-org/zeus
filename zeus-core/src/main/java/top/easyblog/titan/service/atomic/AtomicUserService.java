package top.easyblog.titan.service.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.UserMapper;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.dao.auto.model.UserExample;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryUserListRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/29 16:10
 */
@Slf4j
@Service
public class AtomicUserService {

    @Autowired
    private UserMapper userMapper;

    public User insertSelective(CreateUserRequest request) {
        User user = new User();
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, user);
        userMapper.insertSelective(user);
        log.info("[DB]Insert new user sucessfully!Details==>{}",JsonUtils.toJSONString(user));
        return user;
    }


    public User queryByRequest(QueryUserRequest request) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (StringUtils.isNotBlank(request.getCode())) {
            criteria.andCodeEqualTo(request.getCode());
        }
        if (StringUtils.isNotBlank(request.getNickName())) {
            criteria.andNickNameEqualTo(request.getNickName());
        }
        return Iterables.getFirst(userMapper.selectByExample(userExample), null);
    }

    public List<User> queryUserListByRequest(QueryUserListRequest request) {
        return userMapper.selectByExample(generateExamples(request));
    }

    private UserExample generateExamples(QueryUserListRequest request) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getNickname())) {
            criteria.andNickNameLike("%" + request.getNickname() + "%");
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andActiveEqualTo(request.getStatus());
        }
        if (CollectionUtils.isNotEmpty(request.getCodes())) {
            criteria.andCodeIn(request.getCodes());
        }
        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        return example;
    }

    public void updateUserByPrimaryKey(User user) {
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
        log.info("[DB]Update user by ok successfully!Details==>{}", JsonUtils.toJSONString(user));
    }

    public long countByRequest(QueryUserListRequest request) {
        return userMapper.countByExample(generateExamples(request));
    }
}
