package org.microdeaf.mapper;

import org.mapstruct.Mapper;
import org.microdeaf.mapper.annotation.MicrodeafMapper;
import org.microdeaf.mapper.mapstruct.BaseMapper;

@Mapper
@MicrodeafMapper(entity = Entity.class, view = Entity.class)
public interface MapperTest extends BaseMapper<Entity, View> {

    View toView(Entity entity);

    Entity toEntity(View view);
}
