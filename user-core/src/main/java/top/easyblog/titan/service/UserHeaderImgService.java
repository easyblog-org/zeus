package top.easyblog.titan.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.UserHeaderImgBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.UserHeaderImg;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.request.UpdateUserHeaderImgRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessUserHeaderImgService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 13:19
 */
@Service
public class UserHeaderImgService {

    @Autowired
    private AccessUserHeaderImgService headerImgService;


    @Transaction
    public void createUserHeaderImg(CreateUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        headerImgService.createUserHeaderImgSelective(request);
    }

    @Transaction
    public UserHeaderImgBean updateUserHeaderImg(UpdateUserHeaderImgRequest request) {
        headerImgService.updateHeaderImgByRequest(request);
        return null;
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
            return userHeaderImgBean;
        }).collect(Collectors.toList());
    }
}
