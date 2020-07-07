/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microdeaf.mapper.factory;

import lombok.extern.slf4j.Slf4j;
import org.microdeaf.mapper.web.method.MethodArgumentResolver;
import org.microdeaf.mapper.web.method.MethodReturnValueHandler;
import org.microdeaf.mapper.web.method.RequestResponseMethod;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelProcessorHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is factory bean for add {@link MethodReturnValueHandler}
 * and {@link MethodArgumentResolver} classes into
 * list of spring handlers.
 *
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 * @since 2020-07-06
 */

@Slf4j
public class RequestResponseFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    public RequestResponseFactoryBean(String basePackage) {
        RequestResponseMethod.getInstance(basePackage);
    }

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
                        new MethodReturnValueHandler(handlerMethodReturnValueHandler);
                handlerMethodReturnValueHandlers.set(handlerMethodReturnValueHandlers.indexOf(handlerMethodReturnValueHandler), methodReturnValueHandler);
                log.info("add methodReturnValueHandler into handlerMethodReturnValueHandlers list");
                break;
            }
        }
        for (HandlerMethodArgumentResolver handlerMethodArgumentResolver : handlerMethodArgumentResolvers) {
            if (handlerMethodArgumentResolver instanceof RequestResponseBodyMethodProcessor) {
                MethodArgumentResolver methodArgumentResolver =
                        new MethodArgumentResolver(handlerMethodArgumentResolver, requestMappingHandlerAdapter);
                handlerMethodArgumentResolvers.set(handlerMethodArgumentResolvers
                        .indexOf(handlerMethodArgumentResolver), methodArgumentResolver);
                log.info("add methodArgumentResolver into handlerMethodArgumentResolvers");
                break;
            }
        }
    }

}