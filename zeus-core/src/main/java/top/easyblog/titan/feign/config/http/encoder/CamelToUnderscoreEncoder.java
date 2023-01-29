package top.easyblog.titan.feign.config.http.encoder;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import feign.QueryMapEncoder;
import feign.codec.EncodeException;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 驼峰样式参数转换下换线样式参数
 *
 * @author: frank.huang
 * @date: 2022-03-01 20:55
 */
@Slf4j
public class CamelToUnderscoreEncoder implements QueryMapEncoder {


    private final Map<Class<?>, ObjectParamMetadata> classToMetadata = Maps.newHashMap();

    @Override
    public Map<String, Object> encode(Object o) {
        try {
            ObjectParamMetadata metadata = getMetadata(o.getClass());
            Map<String, Object> propertyNameToValue = Maps.newHashMap();
            for (PropertyDescriptor pd : metadata.objectProperties) {
                Object value = pd.getReadMethod().invoke(o);
                if (null != value && value != o) {
                    //将驼峰类型参数转为下划线
                    String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, pd.getName());
                    propertyNameToValue.put(name, value);
                }
            }
            return propertyNameToValue;
        } catch (Exception e) {
            throw new EncodeException("Failure encoding object into query map:", e);
        }
    }

    private ObjectParamMetadata getMetadata(Class<?> type) throws IntrospectionException {
        ObjectParamMetadata metadata = classToMetadata.get(type);
        if (Objects.isNull(metadata)) {
            metadata = ObjectParamMetadata.parseObjectType(type);
            classToMetadata.put(type, metadata);
        }
        return metadata;
    }


    private static class ObjectParamMetadata {
        private final List<PropertyDescriptor> objectProperties;

        private ObjectParamMetadata(List<PropertyDescriptor> objectProperties) {
            this.objectProperties = objectProperties;
        }

        private static ObjectParamMetadata parseObjectType(Class<?> type) throws IntrospectionException {
            List<PropertyDescriptor> properties = new ArrayList<>();

            for (PropertyDescriptor pd : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
                boolean isGetMethod = pd.getReadMethod() != null && !"class".equals(pd.getName());
                if (isGetMethod) {
                    properties.add(pd);
                }
            }
            return new ObjectParamMetadata(properties);
        }

    }
}
