package top.easyblog.titan.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.*;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.context.CreateOrRefreshUserRoleContext;
import top.easyblog.titan.context.QueryUserSectionContext;
import top.easyblog.titan.dao.auto.model.Roles;
import top.easyblog.titan.dao.auto.model.User;
import top.easyblog.titan.dao.auto.model.UserRoles;
import top.easyblog.titan.enums.Status;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.*;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.atomic.AtomicRolesService;
import top.easyblog.titan.service.atomic.AtomicUserRolesService;
import top.easyblog.titan.service.atomic.AtomicUserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static top.easyblog.titan.constant.LoginConstants.*;

/**
 * @author frank.huang
 * @date 2022/01/30 10:43
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private AtomicUserService atomicUserService;

    @Autowired
    private UserHeaderImgService headerImgService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserSignInLogService userSignInLogService;

    @Autowired
    private AtomicUserRolesService atomicUserRolesService;

    @Autowired
    private RolesService rolesService;

    /**
     * 查询用户详情
     *
     * @param request
     * @return
     */
    @Transaction
    public UserDetailsBean queryUserDetails(QueryUserRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        //1.根据request查询user基本信息
        User user = atomicUserService.queryByRequest(request);
        if (Objects.isNull(user)) {
            return null;
        }
        UserDetailsBean userDetailsBean = buildUserDetailsBean(user);
        //查询其他选项参数
        fillSection(request.getSections(), Collections.singletonList(userDetailsBean));
        return userDetailsBean;
    }

    private UserDetailsBean buildUserDetailsBean(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserDetailsBean userDetailsBean = new UserDetailsBean();
        BeanUtils.copyProperties(user, userDetailsBean);
        userDetailsBean.setIsNewUser(Boolean.FALSE);
        return userDetailsBean;
    }


    /**
     * 查看可选值
     *
     * @param section
     * @return
     */
    private QueryUserSectionContext queryUserSectionSections(String section, List<Long> userIds) {
        QueryUserSectionContext context = QueryUserSectionContext.builder().build();
        if (CollectionUtils.isEmpty(userIds)) {
            return context;
        }

        if (section.contains(QUERY_HEADER_IMG)) {
            QueryUserHeaderImgsRequest queryUserHeaderImgsRequest = QueryUserHeaderImgsRequest.builder()
                    .userIds(userIds).status(Status.ENABLE.getCode()).build();
            List<UserHeaderImgBean> userHeaderImgBeans = headerImgService.queryUserHeaderImgBeans(queryUserHeaderImgsRequest);
            Map<Long, List<UserHeaderImgBean>> userHeaderImgBeanMap = userHeaderImgBeans.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(UserHeaderImgBean::getUserId));
            context.setUserHistoryImagesMap(userHeaderImgBeanMap);
        }
        if (section.contains(QUERY_CURRENT_HEADER_IMG)) {
            QueryUserHeaderImgsRequest queryUserHeaderImgsRequest = QueryUserHeaderImgsRequest.builder()
                    .userIds(userIds).status(Status.ENABLE.getCode()).build();
            List<UserHeaderImgBean> userHeaderImgBeans = headerImgService.queryUserHeaderImgBeans(queryUserHeaderImgsRequest);
            Map<Long, UserHeaderImgBean> userHeaderImgBeanMap = userHeaderImgBeans.stream().
                    filter(item -> Boolean.TRUE.equals(item.getIsCurrentHeader()))
                    .collect(Collectors.toMap(UserHeaderImgBean::getUserId, Function.identity(), (x, y) -> x));
            context.setUserCurrentImagesMap(userHeaderImgBeanMap);
        }
        if (section.contains(QUERY_ACCOUNTS)) {
            QueryAccountListRequest queryAccountListRequest = QueryAccountListRequest.builder()
                    .userIds(userIds).status(Status.ENABLE.getCode()).build();
            List<AccountBean> accounts = accountService.queryAccountList(queryAccountListRequest);
            Map<Long, List<AccountBean>> accountMap = accounts.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(AccountBean::getUserId));
            context.setAccountsMap(accountMap);
        }
        if (section.contains(QUERY_SIGN_LOG)) {
            QuerySignInLogListRequest querySignInLogListRequest = QuerySignInLogListRequest.builder()
                    .userIds(userIds).status(Status.ENABLE.getCode()).offset(Constants.DEFAULT_OFFSET).limit(Constants.DEFAULT_LIMIT).build();
            PageResponse<SignInLogBean> signInLogBeanPageResponse = userSignInLogService.querySignInLogList(querySignInLogListRequest);
            List<SignInLogBean> signInLogBeans = signInLogBeanPageResponse.getList();
            Map<Long, List<SignInLogBean>> signInLogBeanMap = signInLogBeans.stream().filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(SignInLogBean::getUserId));
            context.setSignInLogsMap(signInLogBeanMap);
        }
        if (section.contains(QUERY_ROLE)) {
            List<UserRoles> userRoles = atomicUserRolesService.queryList(QueryUserRolesListRequest.builder()
                    .userIds(userIds).enabled(Boolean.TRUE).build());

            if (CollectionUtils.isNotEmpty(userRoles)) {
                Map<Long, Long> userRoleIdMap = userRoles.stream().filter(Objects::nonNull).collect(Collectors.toMap(UserRoles::getRoleId, UserRoles::getUserId, (x, y) -> x));
                List<Long> roleIds = userRoles.stream().map(UserRoles::getRoleId).collect(Collectors.toList());
                PageResponse<RolesBean> rolesBeanPageResponse = rolesService.queryRolesList(QueryRolesListRequest.builder()
                        .ids(roleIds).build());
                List<RolesBean> rolesBeans = rolesBeanPageResponse.getList();
                Map<Long, List<RolesBean>> rolesIdMap = rolesBeans.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(RolesBean::getId));
                Map<Long, List<RolesBean>> userIdRoleMap = Maps.newHashMap();
                userRoleIdMap.forEach((roleId, userId) -> {
                    userIdRoleMap.putIfAbsent(userId, rolesIdMap.get(roleId));
                });
                context.setRolesMap(userIdRoleMap);
            }
        }

        return context;
    }

    /**
     * 设置选项
     *
     * @param section
     * @param userDetailsBeans
     */
    private void fillSection(String section, List<UserDetailsBean> userDetailsBeans) {
        if (StringUtils.isBlank(section) || CollectionUtils.isEmpty(userDetailsBeans)) {
            log.info("Not found any section param or user list is empty,will not fill section");
            return;
        }

        List<Long> userIds = userDetailsBeans.stream().map(UserDetailsBean::getId).collect(Collectors.toList());
        QueryUserSectionContext context = queryUserSectionSections(section, userIds);
        userDetailsBeans.stream().filter(Objects::nonNull).forEach(userDetailsBean -> {
            userDetailsBean.setUserCurrentImages(getSectionOptional(context.getUserCurrentImagesMap(),userDetailsBean.getId()));
            userDetailsBean.setUserHistoryImages(getSectionOptional(context.getUserHistoryImagesMap(),userDetailsBean.getId()));
            userDetailsBean.setAccounts(getSectionOptional(context.getAccountsMap(),userDetailsBean.getId()));
            userDetailsBean.setRoles(getSectionOptional(context.getRolesMap(),userDetailsBean.getId()));
            userDetailsBean.setSignInLogs(getSectionOptional(context.getSignInLogsMap(),userDetailsBean.getId()));
        });
    }


    private <T> T getSectionOptional(Map<Long, T> contextMap, Long key) {
        return Optional.ofNullable(contextMap).map(map -> map.get(key)).orElse(null);
    }

    /**
     * 更新用户信息
     *
     * @param request
     */
    public Long updateUser(String code, UpdateUserRequest request) {
        User user = atomicUserService.queryByRequest(QueryUserRequest.builder()
                .code(code).build());
        if (Objects.isNull(user)) {
            throw new BusinessException(ZeusResultCode.USER_NOT_FOUND);
        }
        User newUser = new User();
        newUser.setId(user.getId());
        BeanUtils.copyProperties(request, newUser);
        atomicUserService.updateUserByPrimaryKey(newUser);

        createOrRefreshUserRole(CreateOrRefreshUserRoleContext.builder()
                .userId(user.getId()).roles(request.getRoles()).build());
        return user.getId();
    }

    /**
     * 查询列表，支持分页
     *
     * @param request
     * @return
     */
    @Transaction
    public PageResponse<UserDetailsBean> queryUserListPage(QueryUserListRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页，默认查询1000条数据
            request.setOffset(Constants.DEFAULT_OFFSET);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.DEFAULT_LIMIT : request.getLimit());
            List<UserDetailsBean> userDetailsBeans = buildUserDetailsBeanList(request);
            return new PageResponse<>(request.getLimit(), request.getOffset(),
                    (long) userDetailsBeans.size(), userDetailsBeans);
        }
        PageResponse<UserDetailsBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                0L, Collections.emptyList());
        long count = atomicUserService.countByRequest(request);
        if (count == 0) {
            return response;
        }
        response.setTotal(count);
        response.setList(buildUserDetailsBeanList(request));
        return response;
    }

    private List<UserDetailsBean> buildUserDetailsBeanList(QueryUserListRequest request) {
        List<UserDetailsBean> userDetailsBeans = atomicUserService.queryUserListByRequest(request).stream().map(user -> {
            UserDetailsBean userDetailsBean = new UserDetailsBean();
            BeanUtils.copyProperties(user, userDetailsBean);
            return userDetailsBean;
        }).collect(Collectors.toList());

        fillSection(request.getSections(), userDetailsBeans);
        return userDetailsBeans;
    }


    /**
     * 创建用户
     *
     * @param request
     */
    @Transaction
    public UserDetailsBean createUser(CreateUserRequest request) {
        User user = atomicUserService.queryByRequest(QueryUserRequest.builder()
                .nickName(request.getNickName()).build());
        if (Objects.nonNull(user)) {
            // nickname 不能重复，如果重复返回已经存在的用户
            return buildUserDetailsBean(user);
        }
        User newUser = atomicUserService.insertSelective(request);
        UserDetailsBean userDetailsBean = buildUserDetailsBean(newUser);
        Objects.requireNonNull(userDetailsBean).setIsNewUser(Boolean.TRUE);

        // 创建 or 更新用户角色
        createOrRefreshUserRole(CreateOrRefreshUserRoleContext.builder()
                .userId(newUser.getId()).roles(request.getRoles()).build());

        return userDetailsBean;
    }

    private void createOrRefreshUserRole(CreateOrRefreshUserRoleContext context) {
        List<String> roles = context.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            log.info("Empty role list.....ignore!");
            return;
        }

        List<RolesBean> rolesBeans = rolesService.queryAllRolesList();
        if (CollectionUtils.isEmpty(rolesBeans)) {
            throw new BusinessException(ZeusResultCode.ROLE_NOT_FOUND);
        }

        Map<String, RolesBean> rolesBeanMap = rolesBeans.stream().collect(Collectors.toMap(RolesBean::getCode, Function.identity(), (x, y) -> x));

        // 存在 user-role 映射关系，删除老的
        long userRoleCount = atomicUserRolesService.countByRequest(QueryUserRolesListRequest.builder()
                .userIds(Collections.singletonList(context.getUserId())).enabled(Boolean.TRUE).build());
        if (userRoleCount > 0) {
            UserRoles userRoles = new UserRoles();
            userRoles.setEnabled(Boolean.FALSE);
            atomicUserRolesService.updateByExampleSelective(userRoles, UpdateUserRolesRequest.builder()
                    .userId(context.getUserId()).build());
        }

        roles.forEach(roleCode -> {
            RolesBean rolesBean = rolesBeanMap.get(roleCode);
            UserRoles userRoles = new UserRoles();
            userRoles.setRoleId(Objects.requireNonNull(rolesBean, String.format("Role %s not found", roleCode)).getId());
            userRoles.setUserId(context.getUserId());
            userRoles.setEnabled(Boolean.TRUE);
            atomicUserRolesService.insertOne(userRoles);
        });
    }


}
