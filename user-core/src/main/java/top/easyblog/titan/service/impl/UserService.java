package top.easyblog.titan.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.AccountBean;
import top.easyblog.titan.bean.SignInLogBean;
import top.easyblog.titan.bean.UserDetailsBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreateUserRequest;
import top.easyblog.titan.request.QueryAccountListRequest;
import top.easyblog.titan.request.QuerySignInLogListRequest;
import top.easyblog.titan.request.QueryUserHeaderImgsRequest;
import top.easyblog.titan.request.QueryUserListRequest;
import top.easyblog.titan.request.QueryUserRequest;
import top.easyblog.titan.request.UpdateUserRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ResultCode;
import top.easyblog.titan.service.data.AccessUserService;

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

    private static final String QUERY_HEADER_IMG = "header_img";

    private static final String QUERY_ACCOUNTS = "accounts";

    private static final String QUERY_SIGN_LOG = "sign_log";

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
        //1.根据request查询user基本信息
        User user = userService.queryByRequest(request);
        if (Objects.isNull(user)) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        UserDetailsBean userDetailsBean = new UserDetailsBean();
        BeanUtils.copyProperties(user, userDetailsBean);
        //查询其他选项参数
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
            if (section.contains(QUERY_HEADER_IMG)) {
                QueryUserHeaderImgsRequest queryUserHeaderImgsRequest = QueryUserHeaderImgsRequest.builder().userId(userDetailsBean.getId()).build();
                userDetailsBean.setUserHeaderImg(headerImgService.buildUserHeaderImgBeans(queryUserHeaderImgsRequest));
            }
            if (section.contains(QUERY_ACCOUNTS)) {
                QueryAccountListRequest queryAccountListRequest = QueryAccountListRequest.builder().userId(userDetailsBean.getId()).status(Status.ENABLE.getCode()).build();
                List<AccountBean> accounts = accountService.queryAccountList(queryAccountListRequest);
                userDetailsBean.setAccounts(accounts);
            }
            if (section.contains(QUERY_SIGN_LOG)) {
                QuerySignInLogListRequest querySignInLogListRequest = new QuerySignInLogListRequest();
                querySignInLogListRequest.setUserId(userDetailsBean.getId());
                querySignInLogListRequest.setStatus(Status.ENABLE.getCode());
                querySignInLogListRequest.setOffset(Constants.DEFAULT_OFFSET);
                querySignInLogListRequest.setLimit(Constants.DEFAULT_LIMIT);
                PageResponse<SignInLogBean> signInLogBeanPageResponse = userSignInLogService.querySignInLogList(querySignInLogListRequest);
                userDetailsBean.setSignInLogs(signInLogBeanPageResponse.getData());
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
    @Transaction
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
    @Transaction
    public void createUser(CreateUserRequest request) {
        userService.insertSelective(request);
    }


}
