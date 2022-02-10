package top.easyblog.titan.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessUserService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author frank.huang
 * @date 2022/01/30 10:43
 */
@Service
public class UserService {

    @Autowired
    private AccessUserService userService;

    @Autowired
    private UserHeaderImgService headerImgService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private UserSignInLogService userSignInLogService;

    private static final String QUERY_ACCOUNT_LIST_FLAG = "accounts";

    private static final String QUERY_SIGN_LOG_LIST_FLAG = "sign_log";

    /**
     * 查询用户详情
     *
     * @param request
     * @return
     */
    @Transaction
    public UserDetailsBean queryUserDetails(QueryUserRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        //1.根据request查询user信息
        User user = userService.queryByRequest(request);
        if (Objects.isNull(user)) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        UserDetailsBean userDetailsBean = new UserDetailsBean();
        BeanUtils.copyProperties(user, userDetailsBean);
        //2.查询user头像
        userDetailsBean.setUserHeaderImg(headerImgService.queryUserHeaderDetails(QueryUserHeaderImgRequest.builder()
                .userId(user.getId()).statuses(Lists.newArrayList(Status.ENABLE.getCode())).build()));
        setSection(request.getSections(), userDetailsBean);
        return userDetailsBean;
    }

    /**
     * 设置选项
     *
     * @param section
     * @param userDetailsBean
     */
    private void setSection(String section, UserDetailsBean userDetailsBean) {
        if (StringUtils.isNotBlank(section)) {
            if (section.contains(QUERY_ACCOUNT_LIST_FLAG)) {
                //TODO
                userDetailsBean.setAccounts(null);
            }
            if (section.contains(QUERY_SIGN_LOG_LIST_FLAG)) {
                // TODO
                userDetailsBean.setSignInLogs(null);
            }
        }
    }

    /**
     * 更新用户信息
     *
     * @param request
     */
    public void updateUser(UpdateUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        userService.updateUserByPrimaryKey(user);
    }

    /**
     * 查询列表，支持分页
     *
     * @param request
     * @return
     */
    public Object queryUserListPage(QueryUserListRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页，默认查询1000条数据
            request.setOffset(Constants.DEFAULT_OFFSET);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.DEFAULT_LIMIT : request.getLimit());
            return buildUserDetailsBeanList(request);
        }
        PageResponse<UserDetailsBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                0L, Collections.emptyList());
        long count = userService.countByRequest(request);
        if (count == 0) {
            return response;
        }
        response.setTotal(count);
        response.setData(buildUserDetailsBeanList(request));
        return response;
    }

    private List<UserDetailsBean> buildUserDetailsBeanList(QueryUserListRequest request) {
        return userService.queryUserListByRequest(request).stream().map(user -> {
            UserDetailsBean userDetailsBean = new UserDetailsBean();
            BeanUtils.copyProperties(user, userDetailsBean);
            setSection(request.getSections(), userDetailsBean);
            return userDetailsBean;
        }).collect(Collectors.toList());
    }


    /**
     * 创建用户
     *
     * @param request
     */
    public void createUser(CreateUserRequest request) {
        userService.insertSelective(request);
    }


}
