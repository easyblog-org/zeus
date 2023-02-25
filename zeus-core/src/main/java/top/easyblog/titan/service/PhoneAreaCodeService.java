package top.easyblog.titan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.easyblog.titan.annotation.Transaction;
import top.easyblog.titan.bean.PhoneAreaCodeBean;
import top.easyblog.titan.constant.Constants;
import top.easyblog.titan.dao.auto.model.PhoneAreaCode;
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

    @Transaction
    public void createPhoneAreaCode(CreatePhoneAreaCodeRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }
        PhoneAreaCodeBean phoneAreaCodeBean = queryPhoneAreaCodeDetails(QueryPhoneAreaCodeRequest.builder()
                .crownCode(request.getCrownCode()).countryCode(request.getCountryCode()).build());
        if (Objects.nonNull(phoneAreaCodeBean)) {
            throw new BusinessException(ZeusResultCode.PHONE_AREA_CODE_ALREADY_EXISTS);
        }
        atomicPhoneAreaCodeService.insertPhoneAreaCodeByRequest(request);
    }

    @Transaction
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

    @Transaction
    public Object queryPhoneAreaCodePage(QueryPhoneAreaCodeListRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }

        if (Objects.isNull(request.getOffset()) || Objects.isNull(request.getLimit())) {
            //不分页,默认查询前1000条数据
            request.setOffset(Constants.DEFAULT_OFFSET);
            request.setLimit(Objects.isNull(request.getLimit()) ? Constants.DEFAULT_LIMIT : request.getLimit());
            return buildPhoneAreaCodeBeanList(request);
        }
        //分页
        PageResponse<PhoneAreaCodeBean> response = new PageResponse<>(request.getLimit(), request.getOffset(),
                0L, Collections.emptyList());
        long count = atomicPhoneAreaCodeService.countByRequest(request);
        if (count == 0) {
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

    @Transaction
    public void updatePhoneAreaCode(UpdatePhoneAreaCodeRequest request) {
        if (Objects.isNull(request)) {
            throw new BusinessException(ZeusResultCode.REQUIRED_REQUEST_PARAM_NOT_EXISTS);
        }

        PhoneAreaCode phoneAreaCode = new PhoneAreaCode();
        BeanUtils.copyProperties(request, phoneAreaCode);
        atomicPhoneAreaCodeService.updatePhoneAreaCodeByRequest(phoneAreaCode);
    }
}
