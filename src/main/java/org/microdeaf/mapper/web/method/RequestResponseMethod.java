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

import org.microdeaf.mapper.annotation.MicrodeafMapper;
import org.microdeaf.mapper.enums.ClassType;
import org.microdeaf.mapper.mapstruct.BaseMapper;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 *  @since 2020-07-06
 */
public class RequestResponseMethod {
    private static RequestResponseMethod ourInstance;
    private static Set<Class<?>> classes;

    /**
     * This is static method that return an object from this class.
     * If an object is not created, an object is created
     * from this class and then  return the created object.
     *
     * @return an object from this class.
     */
    public static RequestResponseMethod getInstance() {
        if (ourInstance == null) {
            ourInstance = new RequestResponseMethod();
            return ourInstance;
        }
        return ourInstance;
    }

    /**
     * This is static method that return an object from this class.
     * If an object is not created, an object is created
     * from the class, and then it finds the classes that
     * have the {@link MicrodeafMapper} annotation and
     * finally return the created object.
     *
     * @return an object from this class.
     */
    public static RequestResponseMethod getInstance(String basePackage) {
        if (ourInstance == null) {
            ourInstance = new RequestResponseMethod();
            Reflections ref = new Reflections(basePackage);
            classes = ref.getTypesAnnotatedWith(MicrodeafMapper.class);
            return ourInstance;
        }
        return ourInstance;
    }

    private RequestResponseMethod() {
    }

    /**
     * @param source is an object that wanted map to target object
     * @param type is enum class that specify the type of target object
     * @return target of source class.
     */
    public Class target(Class source, ClassType type) {
        for (Class<?> clazz : classes) {
            MicrodeafMapper annotation = clazz.getAnnotation(MicrodeafMapper.class);
            if (annotation != null) {
                if (type == ClassType.VIEW && annotation.entity().getSimpleName().equals(source.getSimpleName())) {
                    return annotation.view();
                } else if (type == ClassType.ENTITY && annotation.view().getSimpleName().equals(source.getSimpleName())) {
                    return annotation.entity();
                }
            }
        }
        return source;
    }

    /**
     * @param source is an object that specify the mapper class
     * @return mapper of source class.
     */
    public Class<? extends BaseMapper> mapper(Class source) {
        for (Class<?> clazz : classes) {
            MicrodeafMapper annotation = clazz.getAnnotation(MicrodeafMapper.class);
            if (annotation != null) {
                if (annotation.entity().getSimpleName().equals(source.getSimpleName()) ||
                        annotation.view().getSimpleName().equals(source.getSimpleName())) {
                    return (Class<? extends BaseMapper>) clazz;
                }
            }
        }
        return null;
    }

}
