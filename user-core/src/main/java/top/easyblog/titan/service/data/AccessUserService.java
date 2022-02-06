package top.easyblog.titan.service.data;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import top.easyblog.titan.dao.auto.mapper.UserMapper;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.dao.auto.model.UserExample;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.QueryUsersRequest;

/**
 * @author frank.huang
 * @date 2022/01/29 16:10
 */
@Service
public class AccessUserService {

    @Autowired
    private UserMapper userMapper;

    public User insertSelective(CreateUserRequest request) {
        User user = new User();
        request.setCreateTime(new Date());
        request.setUpdateTime(new Date());
        BeanUtils.copyProperties(request, user);
        userMapper.insertSelective(user);
        return user;
    }


    public User queryByRequest(QueryUserRequest request) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (StringUtils.isNotBlank(request.getNickName())) {
            criteria.andNickNameEqualTo(request.getNickName());
        }
        return Iterables.getFirst(userMapper.selectByExample(userExample), null);
    }

    public List<User> queryUserListByRequest(QueryUsersRequest request) {
        return userMapper.selectByExample(generateExamples(request));
    }

    private UserExample generateExamples(QueryUsersRequest request) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (CollectionUtils.isNotEmpty(request.getIds())) {
            criteria.andIdIn(request.getIds());
        }

        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        return example;
    }

    public void updateUser(User user) {
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    public long countByRequest(QueryUsersRequest request) {
        return userMapper.countByExample(generateExamples(request));
    }
}
