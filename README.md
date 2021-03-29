# Deaf Mapper 2.0.1

## Why Map?
A mapping framework is useful in a layered architecture where you are creating layers of abstraction by encapsulating changes to particular data objects vs. propagating these objects to other layers (i.e. external service data objects, domain objects, data transfer objects, internal service data objects).

Mapping between data objects has traditionally been addressed by hand coding value object assemblers (or converters) that copy data between the objects. Most programmers will develop some sort of custom mapping framework and spend countless hours and thousands of lines of code mapping to and from their different data object.

## What is Deaf Mapper?
In MVC architecture, when the data is sent from the View layer to the Controller layer, in this layer the objects convert through mapper frameworks to objects of the Model layer objects. This module actually does this completely automatically, instead of manually converting objects sent from the View layer to Model layer objects from the Controller layer.
Deaf Mapper is a module that integrates mapper frameworks such as [mapstruct](https://mapstruct.org/), [modelmapper](http://modelmapper.org/) with spring boot projects. 
This version uses from [mapstruct](https://mapstruct.org/) to mapping data objects of the Model layer to dto objects of the View layer and conversely.

## Getting Started

If you are using Maven, simply copy-paste this repository and dependency to your project.

```
    <repositories>
        <repository>
            <id>github</id>
            <name>GitHub microdeaf Apache Maven Packages</name>
            <url>https://maven.pkg.github.com/microdeaf/deaf-mapper</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```
```
    <dependency>
        <groupId>org.microdeaf</groupId>
        <artifactId>deaf-mapper</artifactId>
        <version>2.0.1</version>
    </dependency>
```
You must create a bean from RequestResponseFactoryBean in configuration class:

```
    @Bean
    public RequestResponseFactoryBean deafMapperFactory() {
        return new RequestResponseFactoryBean(mapperBasePackage);
    }
```
and when define mapstruct interface, your mapper must extend from BaseMapper interface and uses of @MicrodeafMapper annotation to define entity and view class for mapping automatically in the Controller layer :
```
    @Mapper
    @MicrodeafMapper(entity = Ability.class, view = AbilityView.class)
    public interface AbilityMapper extends BaseMapper<Ability, AbilityView> {

        AbilityView toView(Ability entity);

        Ability toEntity(AbilityView view);

    }
```
In the Controller layer, your API input no longer needs to be a DTO object from the View layer. This is because Deaf Mapper automatically converts the DTO object to a Model layer object and conversely. For example, according to the code defined above, the method written for API saveAbility in a project with Spring Boot is as follows:

```
    @PostMapping("/saveAbility")
    public void saveAbility(@RequestBody Ability entity) {
        ...
    }
```

## License

The Deaf Mapper is released under version 2.0.1 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
