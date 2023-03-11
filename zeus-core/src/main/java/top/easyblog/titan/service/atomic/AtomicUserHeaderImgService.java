package top.easyblog.titan.service.atomic;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.dao.auto.mapper.UserHeaderImgMapper;
import top.easyblog.titan.dao.auto.model.UserHeaderImg;
import top.easyblog.titan.dao.auto.model.UserHeaderImgExample;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.request.CreateUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.util.JsonUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author frank.huang
 * @date 2022/01/30 10:53
 */
@Slf4j
@Service
public class AtomicUserHeaderImgService {

    @Autowired
    private UserHeaderImgMapper userHeaderImgMapper;


    public void createUserHeaderImgSelective(CreateUserHeaderImgRequest request) {
        UserHeaderImg userHeaderImg = new UserHeaderImg();
        userHeaderImg.setCreateTime(new Date());
        userHeaderImg.setUpdateTime(new Date());
        userHeaderImg.setStatus(Status.ENABLE.getCode());
        BeanUtils.copyProperties(request, userHeaderImg);
        userHeaderImgMapper.insertSelective(userHeaderImg);
        log.info("[DB]Insert new header images: {}", JsonUtils.toJSONString(userHeaderImg));
    }


    public UserHeaderImg queryByRequest(QueryUserHeaderImgRequest request) {
        if (Objects.isNull(request.getId()) &&
                Objects.isNull(request.getUserId()) &&
                Objects.nonNull(request.getStatus())) {
            return null;
        }
        UserHeaderImgExample example = new UserHeaderImgExample();
        UserHeaderImgExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (Objects.nonNull(request.getUserId())) {
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        return Iterables.getFirst(userHeaderImgMapper.selectByExample(example), null);
    }


    public List<UserHeaderImg> queryHeaderImgListByRequest(QueryUserHeaderImgsRequest request) {
        UserHeaderImgExample example = generateUserHeaderImgExamples(request);
        return userHeaderImgMapper.selectByExample(example);
    }

    public long countByRequest(QueryUserHeaderImgsRequest request) {
        return userHeaderImgMapper.countByExample(generateUserHeaderImgExamples(request));
    }

    private UserHeaderImgExample generateUserHeaderImgExamples(QueryUserHeaderImgsRequest request) {
        UserHeaderImgExample example = new UserHeaderImgExample();
        UserHeaderImgExample.Criteria criteria = example.createCriteria();
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
        }

        if (Objects.nonNull(request.getLimit())) {
            example.setLimit(request.getLimit());
        }
        if (Objects.nonNull(request.getOffset())) {
            example.setOffset(request.getOffset());
        }
        return example;
    }

    public void updateHeaderImgByRequest(UserHeaderImg record) {
        record.setUpdateTime(new Date());
        userHeaderImgMapper.updateByPrimaryKeySelective(record);
        log.info("[DB]Update user header images: {}", JsonUtils.toJSONString(record));
    }

}
