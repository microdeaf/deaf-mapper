package org.microdeaf.mapper.web.method;

import org.mapstruct.factory.Mappers;
import org.microdeaf.mapper.mapstruct.ApplicationMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * @author m.khosrojerdi
 */

public class MethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;
    private ApplicationMapper mapper = Mappers.getMapper(ApplicationMapper.class);
    private Environment env;

    public MethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler, Environment env) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
        this.env = env;
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        return handlerMethodReturnValueHandler.supportsReturnType(returnType);
    }

    public void handleReturnValue(Object o, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        Class<?> viewClass = RequestResponseMethod.getInstance().getViewClass(o, env);
        if (viewClass != null) {
            String mapperPath = RequestResponseMethod.getInstance().getMapperPath(o, env);
            if (o instanceof List) {
                List list = (List) o;
                o = mapper.toViews(list, mapperPath);
            } else {
                o = mapper.toView(o, mapperPath);
            }
        }
        handlerMethodReturnValueHandler.handleReturnValue(o, parameter, mavContainer, webRequest);
    }

}