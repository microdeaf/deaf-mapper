package org.microdeaf.mapper.web.processor;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by m.khosrojerdi
 */
public class BodyMethodProcessor extends RequestResponseBodyMethodProcessor {

    public BodyMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    public BodyMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager) {
        super(converters, manager);
    }

    public BodyMethodProcessor(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
        super(converters, requestResponseBodyAdvice);
    }

    public BodyMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory, Type type) throws Exception {
        return readWithMessageConverters(webRequest, parameter, type);
    }

}
