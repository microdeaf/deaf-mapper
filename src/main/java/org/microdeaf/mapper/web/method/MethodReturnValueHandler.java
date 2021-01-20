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
import org.microdeaf.mapper.mapstruct.ApplicationMapper;
import org.microdeaf.mapper.mapstruct.BaseMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * This class handle the all of objects that
 * sent to client and convert all output objects
 * to view objects with central mapper called {@link ApplicationMapper}
 *
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 *  @since 2020-07-06
 */
public class MethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;
    private ApplicationMapper mapper = Mappers.getMapper(ApplicationMapper.class);

    public MethodReturnValueHandler(HandlerMethodReturnValueHandler handlerMethodReturnValueHandler) {
        this.handlerMethodReturnValueHandler = handlerMethodReturnValueHandler;
    }

    public boolean supportsReturnType(MethodParameter returnType) {
        return handlerMethodReturnValueHandler.supportsReturnType(returnType);
    }

    public void handleReturnValue(Object o, MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        Class<? extends BaseMapper> mapperClass;
        if (o instanceof List) {
            List list = (List) o;
            if (list.size() != 0) {
                mapperClass = RequestResponseMethod.getInstance().mapper(list.get(0).getClass());
                o = mapperClass != null ? mapper.toViews(list, mapperClass) : list;
            }
        } else {
            mapperClass = RequestResponseMethod.getInstance().mapper(o.getClass());
            o = mapperClass != null ? mapper.toView(o, mapperClass) : o;
        }
        handlerMethodReturnValueHandler.handleReturnValue(o, parameter, mavContainer, webRequest);
    }

}