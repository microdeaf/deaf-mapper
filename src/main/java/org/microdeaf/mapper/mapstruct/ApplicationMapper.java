package org.microdeaf.mapper.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ApplicationMapper<T, V, M extends BaseMapper<T, V>> {

    @ObjectFactory
    default List<V> toViews(List<T> entities, String path) {
        if (entities != null) {
            List<V> views = new ArrayList();
            for (T o : entities) {
                views.add(toView(o, path));
            }
            return views;
        }
        return null;
    }

    @ObjectFactory
    default List<T> toEntities(List<V> views, String path) {
        if (views != null) {
            List<T> entities = new ArrayList();
            for (V o : views) {
                entities.add(toEntity(o, path));
            }
            return entities;
        }
        return null;
    }

    @ObjectFactory
    default V toView(T entity, String path) {
        try {
            Class<M> mapper = (Class<M>) Class.forName(path + "Mapper");
            return Mappers.getMapper(mapper).toView(entity);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException((e));
        }
    }

    @ObjectFactory
    default T toEntity(V view, String path) {
        try {
            Class<M> mapper = (Class<M>) Class.forName(path + "Mapper");
            return Mappers.getMapper(mapper).toEntity(view);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException((e));
        }
    }

}
