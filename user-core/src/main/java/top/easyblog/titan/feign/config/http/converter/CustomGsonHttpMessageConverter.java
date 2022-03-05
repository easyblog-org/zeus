package top.easyblog.titan.feign.config.http.converter;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import top.easyblog.titan.util.JsonUtils;

import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2021-12-11 11:50
 */
public class CustomGsonHttpMessageConverter extends GsonHttpMessageConverter {


    public CustomGsonHttpMessageConverter() {
        super(JsonUtils.getGson());
        List<MediaType> supportedMediaTypes = Lists.newArrayList(MediaType.APPLICATION_JSON,
                MediaType.TEXT_PLAIN, new MediaType("application", "*+json"));
        setSupportedMediaTypes(supportedMediaTypes);
    }

    @Override
    public void writeInternal(@NotNull Object body, Type type, @NotNull Writer writer) {
        this.getGson().toJson(body, writer);
    }

}
