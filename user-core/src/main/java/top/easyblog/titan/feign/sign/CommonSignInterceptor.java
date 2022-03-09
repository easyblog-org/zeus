package top.easyblog.titan.feign.sign;

import com.google.common.collect.Lists;
import feign.Request;
import feign.RequestTemplate;
import feign.Target;
import org.apache.commons.collections4.CollectionUtils;
import top.easyblog.titan.util.InterceptorUtils;
import top.easyboot.constant.Constants;
import top.easyboot.sign.SignEntity;
import top.easyboot.sign.SignHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: frank.huang
 * @date: 2022-03-01 19:06
 */
public class CommonSignInterceptor extends AbstractSignInterceptor {

    private final Set<String> excludes;

    public CommonSignInterceptor(SignHandler signHandler, Set<String> excludes) {
        super(signHandler);
        this.excludes = excludes;
    }

    @Override
    protected boolean support(RequestTemplate requestTemplate) {
        //排除特殊路径，不需要做验签
        Target<?> target = requestTemplate.feignTarget();
        String url = target.url();
        return CollectionUtils.isEmpty(excludes) || excludes.stream().anyMatch(path -> path.contains(url));
    }

    @Override
    protected SignEntity beforeSign(RequestTemplate requestTemplate) {
        SignEntity signEntity = new SignEntity();
        Request request = requestTemplate.request();
        signEntity.setMethod(request.httpMethod().toString().toUpperCase());
        signEntity.setPath(resolvePath(request));
        String appId = signHandler.getAppId();
        String timestamp = InterceptorUtils.now();
        //将生成的参数绑定到请求url上
        InterceptorUtils.query(requestTemplate, Constants.TIMESTAMP, timestamp);
        InterceptorUtils.query(requestTemplate, Constants.APP_ID, appId);
        //将请求参数放置到SignEntity 用于计算验签
        Map<String, Collection<String>> requestParams = new HashMap<>(requestTemplate.queries());
        requestParams.put(Constants.TIMESTAMP, Lists.newArrayList(timestamp));
        requestParams.put(Constants.APP_ID, Lists.newArrayList(appId));
        signEntity.setPathParams(requestParams);
        return signEntity;
    }

    private String resolvePath(Request request) {
        String url = request.url();
        int index = url.indexOf("?");
        if (index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }

    @Override
    protected void afterSign(RequestTemplate requestTemplate, String sign) {
        InterceptorUtils.query(requestTemplate, Constants.SIGN, sign);
    }


}
