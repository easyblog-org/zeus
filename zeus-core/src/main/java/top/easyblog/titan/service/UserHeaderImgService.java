package top.easyblog.titan.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.easyblog.titan.bean.UserHeaderImgBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.UserHeaderImg;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.atomic.AtomicUserHeaderImgService;

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
    private AtomicUserHeaderImgService headerImgService;

    @Value("${custom.default-header-image}")
    private String defaultHeaderImg;


    public void createUserHeaderImg(CreateUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        UserHeaderImg userHeaderImg = headerImgService.queryByRequest(QueryUserHeaderImgRequest.builder()
                .userId(request.getUserId()).status(Status.ENABLE.getCode()).build());
        //更新用户之前的头像状态为失效
        Optional.ofNullable(userHeaderImg).ifPresent(imgBean -> {
            userHeaderImg.setStatus(Status.DISABLE.getCode());
            headerImgService.updateHeaderImgByRequest(userHeaderImg);
        });
        if (StringUtils.isBlank(request.getHeaderImgUrl())) {
            //设置默认头像
            request.setHeaderImgUrl(defaultHeaderImg);
        }
        headerImgService.createUserHeaderImgSelective(request);
    }


    public UserHeaderImgBean queryUserHeaderDetails(QueryUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        UserHeaderImg userHeaderImg = headerImgService.queryByRequest(request);
        return Optional.ofNullable(userHeaderImg).map(item -> {
            UserHeaderImgBean userHeaderImgBean = new UserHeaderImgBean();
            BeanUtils.copyProperties(userHeaderImg, userHeaderImgBean);
            return userHeaderImgBean;
        }).orElse(null);
    }


    public PageResponse<UserHeaderImgBean> queryUserHeaderList(QueryUserHeaderImgsRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页，默认查询1000条数据
            request.setOffset(Constants.DEFAULT_OFFSET);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.DEFAULT_LIMIT : request.getLimit());
            List<UserHeaderImgBean> userHeaderImgBeans = queryUserHeaderImgBeans(request);
            return PageResponse.<UserHeaderImgBean>builder()
                    .limit(request.getLimit())
                    .offset(request.getOffset())
                    .total((long) userHeaderImgBeans.size())
                    .data(userHeaderImgBeans)
                    .build();
        }
        PageResponse<UserHeaderImgBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                0L, Collections.emptyList());
        long count = headerImgService.countByRequest(request);
        if (count == 0) {
            return response;
        }
        response.setTotal(count);
        response.setData(queryUserHeaderImgBeans(request));
        return response;
    }


    public List<UserHeaderImgBean> queryUserHeaderImgBeans(QueryUserHeaderImgsRequest request) {
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
