package top.easyblog.titan.feign.config.http.converter;

import com.google.common.base.CaseFormat;
import lombok.SneakyThrows;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: frank.huang
 * @date: 2022-03-03 21:02
 */
public class FormHttpMessageConverter extends AbstractGenericHttpMessageConverter<Object> {


    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Charset charset = DEFAULT_CHARSET;

    public FormHttpMessageConverter() {

    }


    public void setCharset(Charset charset) {
        if (charset != this.charset) {
            this.charset = (charset != null ? charset : DEFAULT_CHARSET);
        }
    }


    @Override
    protected void writeInternal(Object obj, Type type, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }

    @Override
    protected MultiValueMap<String, String> readInternal(Class<? extends Object> classOfT, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        MediaType contentType = inputMessage.getHeaders().getContentType();
        Charset charset = (contentType != null && contentType.getCharset() != null ?
                contentType.getCharset() : this.charset);
        String body = StreamUtils.copyToString(inputMessage.getBody(), charset);

        String[] pairs = StringUtils.tokenizeToStringArray(body, "&");
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>(pairs.length);
        for (String pair : pairs) {
            int idx = pair.indexOf('=');
            if (idx == -1) {
                result.add(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, URLDecoder.decode(pair, charset.toString())), null);
            } else {
                String name = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, URLDecoder.decode(pair.substring(0, idx), charset.toString()));
                String value = URLDecoder.decode(pair.substring(idx + 1), charset.toString());
                result.add(name, value);
            }
        }
        return result;
    }

    @SneakyThrows
    @Override
    public Object read(Type type, Class<?> classOfT, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return readInternal(classOfT, inputMessage);
    }
}
