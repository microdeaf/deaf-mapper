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
package org.microdeaf.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation use into interfaces that defined
 * with {@link org.mapstruct.Mapper} and define
 * both entity and view model with {@param entity}
 * and {@param view}, so this params used into
 * {@link org.microdeaf.mapper.web.method.MethodArgumentResolver}
 * and {@link org.microdeaf.mapper.web.method.MethodReturnValueHandler}
 * for convert source classes to target classes.
 *
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 * @since 2020-07-06
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MicrodeafMapper {
    Class entity();

    Class view();
}
