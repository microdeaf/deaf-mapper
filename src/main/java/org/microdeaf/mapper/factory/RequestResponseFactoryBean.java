package org.microdeaf.mapper.factory;

import lombok.extern.slf4j.Slf4j;
import org.microdeaf.mapper.web.method.MethodArgumentResolver;
import org.microdeaf.mapper.web.method.MethodReturnValueHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.server.mvc.RepresentationModelProcessorHandlerMethodReturnValueHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author m.khosrojerdi
 */

@Slf4j
public class RequestResponseFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    private Environment env;

    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>(requestMappingHandlerAdapter.getReturnValueHandlers());
        List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = new ArrayList<>(requestMappingHandlerAdapter.getArgumentResolvers());
        decorateHandlers(handlerMethodReturnValueHandlers, handlerMethodArgumentResolvers);
        requestMappingHandlerAdapter.setReturnValueHandlers(handlerMethodReturnValueHandlers);
        requestMappingHandlerAdapter.setArgumentResolvers(handlerMethodArgumentResolvers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers,
                                  List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers) {
        for (HandlerMethodReturnValueHandler handlerMethodReturnValueHandler : handlerMethodReturnValueHandlers) {
            if (handlerMethodReturnValueHandler instanceof RepresentationModelProcessorHandlerMethodReturnValueHandler) {
                MethodReturnValueHandler methodReturnValueHandler =
                        new MethodReturnValueHandler(handlerMethodReturnValueHandler, env);
                handlerMethodReturnValueHandlers.set(handlerMethodReturnValueHandlers.indexOf(handlerMethodReturnValueHandler), methodReturnValueHandler);
                log.info("add methodReturnValueHandler into handlerMethodReturnValueHandlers list");
                break;
            }
        }
        for (HandlerMethodArgumentResolver handlerMethodArgumentResolver : handlerMethodArgumentResolvers) {
            if (handlerMethodArgumentResolver instanceof RequestResponseBodyMethodProcessor) {
                MethodArgumentResolver methodArgumentResolver =
                        new MethodArgumentResolver(handlerMethodArgumentResolver, requestMappingHandlerAdapter, env);
                handlerMethodArgumentResolvers.set(handlerMethodArgumentResolvers
                        .indexOf(handlerMethodArgumentResolver), methodArgumentResolver);
                log.info("add methodArgumentResolver into handlerMethodArgumentResolvers");
                break;
            }
        }
    }

}