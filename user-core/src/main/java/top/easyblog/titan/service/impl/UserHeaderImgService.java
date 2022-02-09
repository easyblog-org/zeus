package top.easyblog.titan.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import top.easyblog.titan.bean.UserHeaderImgBean;
import top.easyblog.titan.dao.auto.model.UserHeaderImg;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessUserHeaderImgService;

/**
 * @author frank.huang
 * @date 2022/01/30 13:19
 */
@Service
public class UserHeaderImgService {

    @Autowired
    private AccessUserHeaderImgService headerImgService;


    public void createUserHeaderImg(CreateUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }

    }

    public UserHeaderImgBean queryUserHeaderDetails(QueryUserHeaderImgRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        UserHeaderImg userHeaderImg = headerImgService.queryByRequest(request);
        if (Objects.isNull(userHeaderImg)) {
            throw new BusinessException(ResultCode.USER_HEADER_IMG_NOT_FOUND);
        }
        UserHeaderImgBean userHeaderImgBean = new UserHeaderImgBean();
        BeanUtils.copyProperties(userHeaderImg, userHeaderImgBean);
        return userHeaderImgBean;
    }

    public List<UserHeaderImgBean> queryUserHeaderList(QueryUserHeaderImgsRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        List<UserHeaderImg> userHeaderImgs = headerImgService.queryHeaderImgList(request);
        if (CollectionUtils.isEmpty(userHeaderImgs)) {
            throw new BusinessException(ResultCode.USER_HEADER_IMGS_NOT_FOUND);
        }
        return userHeaderImgs.stream().map(header -> {
            UserHeaderImgBean userHeaderImgBean = new UserHeaderImgBean();
            BeanUtils.copyProperties(header, userHeaderImgBean);
            return userHeaderImgBean;
        }).collect(Collectors.toList());
    }
}
