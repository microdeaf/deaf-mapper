# Deaf Mapper 1.0.0

Deaf Mapper is a framework that integrate mapper frameworks with spring boot projects. 
This version use [mapstruct](https://mapstruct.org/) for mapping entity models to dto objects

## Getting Started

To get started, you must create a bean from RequestResponseFactoryBean in configuration class:

```
@Bean
    public RequestResponseFactoryBean deafMapperFactory() {
        return new RequestResponseFactoryBean(mapperBasePackage);
    }
```
and when define mapstruct interface, your mapper must extend from BaseMapper interface:
```
import com.example.model.Ability;
import com.example.view.AbilityView;
import org.mapstruct.Mapper;
import org.microdeaf.mapper.annotation.MicrodeafMapper;
import org.microdeaf.mapper.mapstruct.BaseMapper;

@Mapper
@MicrodeafMapper(entity = Ability.class, view = AbilityView.class)
public interface AbilityMapper extends BaseMapper<Ability, AbilityView> {

    AbilityView toView(Ability entity);

    Ability toEntity(AbilityView view);

}
```
As you can see, on top of the AbilityMapper interface defined a @MicrodeafMapper annotation. because Deaf Mapper 
mapped the view class to entity class and vice versa with this annotation automatically.

## License

The Deaf Mapper is released under version 1.0.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).