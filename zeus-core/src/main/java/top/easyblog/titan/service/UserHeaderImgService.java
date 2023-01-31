package top.easyblog.titan.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.UserHeaderImgBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.UserHeaderImg;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.request.UpdateUserHeaderImgRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.access.AccessUserHeaderImgService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:19
 */
@Service
public class UserHeaderImgService {

    @Autowired
    private AccessUserHeaderImgService headerImgService;

    @Value("${custom.default-header-image}")
    private String defaultHeaderImg;

    @Transaction
    public void createUserHeaderImg(CreateUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        UserHeaderImg userHeaderImg = headerImgService.queryByRequest(QueryUserHeaderImgRequest.builder()
                .userId(request.getUserId()).statuses(Lists.newArrayList(Status.ENABLE.getCode())).build());
        //更新用户之前的头像状态为失效
        Optional.ofNullable(userHeaderImg).ifPresent(imgBean -> headerImgService.updateHeaderImgByRequest(UpdateUserHeaderImgRequest.builder()
                .id(userHeaderImg.getId()).status(Status.DISABLE.getCode()).build()));
        if (StringUtils.isBlank(request.getHeaderImgUrl())) {
            //设置默认头像
            request.setHeaderImgUrl(defaultHeaderImg);
        }
        headerImgService.createUserHeaderImgSelective(request);
    }

    @Transaction
    public UserHeaderImgBean updateUserHeaderImg(UpdateUserHeaderImgRequest request) {
        UserHeaderImg userHeaderImg = headerImgService.queryByRequest(QueryUserHeaderImgRequest.builder()
                .userId(request.getUserId()).statuses(Lists.newArrayList(Status.ENABLE.getCode())).build());
        if (Objects.isNull(userHeaderImg)) {
            throw new BusinessException(ResultCode.USER_HEADER_IMGS_NOT_FOUND);
        }
        headerImgService.updateHeaderImgByRequest(request);
        UserHeaderImgBean userHeaderImgBean = new UserHeaderImgBean();
        BeanUtils.copyProperties(request, userHeaderImgBean);
        userHeaderImgBean.setIsCurrentHeader(!(Objects.nonNull(request.getStatus()) || Status.DISABLE.getCode().equals(request.getStatus())));
        return userHeaderImgBean;
    }

    @Transaction
    public UserHeaderImgBean queryUserHeaderDetails(QueryUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        UserHeaderImg userHeaderImg = headerImgService.queryByRequest(request);
        if (Objects.isNull(userHeaderImg)) {
            return null;
        }
        UserHeaderImgBean userHeaderImgBean = new UserHeaderImgBean();
        BeanUtils.copyProperties(userHeaderImg, userHeaderImgBean);
        return userHeaderImgBean;
    }

    @Transaction
    public Object queryUserHeaderList(QueryUserHeaderImgsRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页，默认查询1000条数据
            request.setOffset(Constants.DEFAULT_OFFSET);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.DEFAULT_LIMIT : request.getLimit());
            return buildUserHeaderImgBeans(request);
        }
        PageResponse<UserHeaderImgBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                0L, Collections.emptyList());
        long count = headerImgService.countByRequest(request);
        if (count == 0) {
            return response;
        }
        response.setTotal(count);
        response.setData(buildUserHeaderImgBeans(request));
        return response;
    }


    public List<UserHeaderImgBean> buildUserHeaderImgBeans(QueryUserHeaderImgsRequest request) {
        return headerImgService.queryHeaderImgListByRequest(request).stream().map(header -> {
            UserHeaderImgBean userHeaderImgBean = new UserHeaderImgBean();
            BeanUtils.copyProperties(header, userHeaderImgBean);
            userHeaderImgBean.setIsCurrentHeader(Status.ENABLE.getCode().equals(header.getStatus()));
            return userHeaderImgBean;
        }).collect(Collectors.toList());
    }


    public String getDefaultUserHeaderImg() {
        return defaultHeaderImg;
    }
}
