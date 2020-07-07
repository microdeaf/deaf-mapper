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
package org.microdeaf.mapper.web.method;

import org.mapstruct.factory.Mappers;
import org.microdeaf.mapper.enums.ClassType;
import org.microdeaf.mapper.mapstruct.ApplicationMapper;
import org.microdeaf.mapper.mapstruct.BaseMapper;
import org.microdeaf.mapper.web.processor.BodyMethodProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * This class handle the all of input objects
 * that sent to the spring controller and
 * convert all of input objects to entity models
 * with central mapper called {@link ApplicationMapper}
 *
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 * @since 2020-07-06
 */
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private BodyMethodProcessor bodyMethodProcessor = null;
    private ApplicationMapper mapper = Mappers.getMapper(ApplicationMapper.class);

    public MethodArgumentResolver(HandlerMethodArgumentResolver handlerMethodArgumentResolver,
                                  RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.handlerMethodArgumentResolver = handlerMethodArgumentResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return handlerMethodArgumentResolver.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object viewClass = RequestResponseMethod.getInstance().target(parameter.getExecutable().getParameterTypes()[0], ClassType.VIEW);
        if (viewClass != null) {
            return returnModel(getBodyMethodProcessor()
                    .resolveArgument(parameter, mavContainer, webRequest, binderFactory, viewClass.getClass()));
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
        Class<? extends BaseMapper> mapperClass = RequestResponseMethod.getInstance().mapper(o.getClass());
        return mapperClass != null ? mapper.toEntity(o, mapperClass) : o;
    }

}
