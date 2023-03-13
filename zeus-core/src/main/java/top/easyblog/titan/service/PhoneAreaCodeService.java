package top.easyblog.titan.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.easyblog.titan.bean.PhoneAreaCodeBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;
import top.easyblog.titan.enums.ContinentEnum;
import top.easyblog.titan.exception.BusinessException;
import top.easyblog.titan.request.CreatePhoneAreaCodeRequest;
import top.easyblog.titan.request.QueryPhoneAreaCodeListRequest;
import top.easyblog.titan.request.QueryPhoneAreaCodeRequest;
import top.easyblog.titan.request.UpdatePhoneAreaCodeRequest;
import top.easyblog.titan.response.PageResponse;
import top.easyblog.titan.response.ZeusResultCode;
import top.easyblog.titan.service.atomic.AtomicPhoneAreaCodeService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: frank.huang
 * @date: 2022-02-10 20:52
 */
@Slf4j
@Service
public class PhoneAreaCodeService {

    @Autowired
    private AtomicPhoneAreaCodeService atomicPhoneAreaCodeService;

    @Value("${custom.batch-delete-password}")
    private String batchDeletePassword;

    public void createPhoneAreaCode(CreatePhoneAreaCodeRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        PhoneAreaCodeBean phoneAreaCodeBean = queryPhoneAreaCodeDetails(QueryPhoneAreaCodeRequest.builder()
                .crownCode(request.getCrownCode()).countryCode(request.getCountryCode()).build());
        if (Objects.nonNull(phoneAreaCodeBean)) {
            throw new BusinessException(ZeusResultCode.PHONE_AREA_CODE_ALREADY_EXISTS);
        }

        ContinentEnum continentEnum = ContinentEnum.codeOf(request.getContinentCode());
        if (Objects.isNull(continentEnum)) {
            throw new BusinessException(ZeusResultCode.INVALID_CONTINENT_TYPE);
        }

        atomicPhoneAreaCodeService.insertPhoneAreaCodeByRequest(request);
    }


    public PhoneAreaCodeBean queryPhoneAreaCodeDetails(QueryPhoneAreaCodeRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        PhoneAreaCode phoneAreaCode = atomicPhoneAreaCodeService.queryPhoneAreaCodeByRequest(request);
        if (Objects.isNull(phoneAreaCode)) {
            return null;
        }
        PhoneAreaCodeBean phoneAreaCodeBean = new PhoneAreaCodeBean();
        BeanUtils.copyProperties(phoneAreaCode, phoneAreaCodeBean);
        return phoneAreaCodeBean;
    }


    public PageResponse<PhoneAreaCodeBean> queryPhoneAreaCodePage(QueryPhoneAreaCodeListRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }

        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页,默认查询前1000条数据
            request.setOffset(Constants.DEFAULT_OFFSET);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.DEFAULT_LIMIT : request.getLimit());
            List<PhoneAreaCodeBean> phoneAreaCodeBeans = buildPhoneAreaCodeBeanList(request);
            return PageResponse.<PhoneAreaCodeBean>builder()
                    .total((long) phoneAreaCodeBeans.size()).data(phoneAreaCodeBeans).limit(request.getLimit()).offset(request.getOffset()).build();
        }
        //分页
        PageResponse<PhoneAreaCodeBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                NumberUtils.LONG_ZERO, Collections.emptyList());
        long count = atomicPhoneAreaCodeService.countByRequest(request);
        if (Objects.equals(NumberUtils.LONG_ZERO, count)) {
            return response;
        }
        response.setTotal(count);
        response.setData(buildPhoneAreaCodeBeanList(request));
        return response;
    }

    private List<PhoneAreaCodeBean> buildPhoneAreaCodeBeanList(QueryPhoneAreaCodeListRequest request) {
        return atomicPhoneAreaCodeService.queryPhoneAreaCodeListByRequest(request).stream().map(phoneAreaCode -> {
            PhoneAreaCodeBean phoneAreaCodeBean = new PhoneAreaCodeBean();
            BeanUtils.copyProperties(phoneAreaCode, phoneAreaCodeBean);
            return phoneAreaCodeBean;
        }).collect(Collectors.toList());
    }


    public void updatePhoneAreaCode(Long areaCodeId, UpdatePhoneAreaCodeRequest request) {
        PhoneAreaCode areaCode = atomicPhoneAreaCodeService.queryPhoneAreaCodeByRequest(QueryPhoneAreaCodeRequest.builder()
                .id(areaCodeId).build());
        if (Objects.isNull(areaCode)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }

        PhoneAreaCode phoneAreaCode = new PhoneAreaCode();
        phoneAreaCode.setId(areaCodeId);
        BeanUtils.copyProperties(request, phoneAreaCode);
        atomicPhoneAreaCodeService.updatePhoneAreaCodeByRequest(phoneAreaCode);
    }


    public void deleteByIds(List<Long> ids, String password) {
        if (CollectionUtils.isEmpty(ids)) {
            log.info("Empty delete phone area code list,ignore operate.");
            return;
        }
        if (StringUtils.isBlank(password) || !StringUtils.equals(password, batchDeletePassword)) {
            throw new BusinessException(ZeusResultCode.DELETE_OPERATION_NOT_PERMISSION);
        }
        atomicPhoneAreaCodeService.deleteByIds(ids);
    }
}
