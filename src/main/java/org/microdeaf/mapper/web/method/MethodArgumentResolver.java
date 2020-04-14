package org.microdeaf.mapper.web.method;

import org.mapstruct.factory.Mappers;
import org.microdeaf.mapper.mapstruct.ApplicationMapper;
import org.microdeaf.mapper.web.processor.BodyMethodProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * Created by m.khosrojerdi
 */
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private BodyMethodProcessor bodyMethodProcessor = null;
    private ApplicationMapper mapper = Mappers.getMapper(ApplicationMapper.class);
    private Environment env;

    public MethodArgumentResolver(HandlerMethodArgumentResolver handlerMethodArgumentResolver,
                                  RequestMappingHandlerAdapter requestMappingHandlerAdapter,
                                  Environment env) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.handlerMethodArgumentResolver = handlerMethodArgumentResolver;
        this.env = env;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return handlerMethodArgumentResolver.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> viewClass = RequestResponseMethod.getInstance().getViewClass(parameter, env);
        if (viewClass != null) {
            return returnModel(getBodyMethodProcessor()
                    .resolveArgument(parameter, mavContainer, webRequest, binderFactory, viewClass));
        } else {
            return getBodyMethodProcessor().resolveArgument(parameter, mavContainer, webRequest,
                    binderFactory);
        }
    }

    private BodyMethodProcessor getBodyMethodProcessor() {
        if (bodyMethodProcessor == null) {
            List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
            bodyMethodProcessor = new BodyMethodProcessor(messageConverters);
        }
        return bodyMethodProcessor;
    }

    private Object returnModel(Object o) {
        return mapper.toEntity(o, RequestResponseMethod.getInstance().getMapperPath(o, env));
    }

}
