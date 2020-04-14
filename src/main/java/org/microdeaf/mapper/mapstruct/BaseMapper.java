package org.microdeaf.mapper.mapstruct;

public interface BaseMapper<T, V> {

    V toView(T entity);

    T toEntity(V view);

}
