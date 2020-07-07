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
package org.microdeaf.mapper.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface is mapper that map entity to
 * view object or map view to entity object
 *
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 * @since 2020-07-06
 */
@Mapper
public interface ApplicationMapper<T, V, M extends BaseMapper<T, V>> {

    @ObjectFactory
    default List<V> toViews(List<T> entities, Class<M> mapper) {
        if (entities != null) {
            List<V> views = new ArrayList<>();
            for (T o : entities) {
                views.add(toView(o, mapper));
            }
            return views;
        }
        return null;
    }

    @ObjectFactory
    default List<T> toEntities(List<V> views, Class<M> mapper) {
        if (views != null) {
            List<T> entities = new ArrayList<>();
            for (V o : views) {
                entities.add(toEntity(o, mapper));
            }
            return entities;
        }
        return null;
    }

    @ObjectFactory
    default V toView(T entity, Class<M> mapper) {
        return Mappers.getMapper(mapper).toView(entity);
    }

    @ObjectFactory
    default T toEntity(V view, Class<M> mapper) {
        return Mappers.getMapper(mapper).toEntity(view);
    }

}
