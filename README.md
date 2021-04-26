# mapstruct-spring-plus
It is an enhanced package of mapstruct and spring, which simplifies the use of mapstruct combined with spring. The implementation method refers to the mapstruct-spring-extensions project

Create Dto and Entity
``` java
@Data
@AutoMap(targetType = Car.class)
public class CarDto {
    private String make;

    @AutoMapField(target = "carType")
    private String type;

}

@Data
public class Car {
    private String make;
    private String carType;
}
```

## Use MapStruct Spring Plus

### Maven
For maven projects, use the following POM file

```xml

...

<properties>
    <org.mapstruct.version>1.4.2.Final</org.mapstruct.version>
    <io.github.zhaord.version>1.0.1.RELEASE</io.github.zhaord.version>
</properties>
...
<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
    <dependency>
        <groupId>io.github.zhaord</groupId>
        <artifactId>mapstruct-spring-plus-boot-starter</artifactId>
        <version>${io.github.zhaord.version}</version>
    </dependency>
</dependencies>
...
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${org.mapstruct.version}</version>
                    </path>
                    <path>
                        <groupId>io.github.zhaord</groupId>
                        <artifactId>mapstruct-spring-plus-processor</artifactId>
                        <version>${io.github.zhaord.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
...

```

###  Gradle
For Gradle projects, use the following configuration

``` gradle
dependencies {
    ...
    compile 'org.mapstruct:mapstruct:1.4.2.Final'
    compile 'io.github.zhaord:mapstruct-spring-plus-boot-starter:1.0.1.RELEASE'

    annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
    testAnnotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final' // if you are using mapstruct in test code

    annotationProcessor 'io.github.zhaord:mapstruct-spring-plus-processor:1.0.1.RELEASE'
    testAnnotationProcessor 'io.github.zhaord:mapstruct-spring-plus-processor:1.0.1.RELEASE' // if you are using mapstruct in test code
    ...
}
```

### Use Cases
Inject `IObjectMapper` in spring, call `objectMapper.map(dto, Car.class)`

``` java

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {AutoMapTests.AutoMapTestConfiguration.class})
public class AutoMapTests {

    @Autowired
    private IObjectMapper mapper;

    @Test
    public void testDtoToEntity() {

        var dto = new CarDto();
        dto.setMake("M1");
        dto.setType("OTHER");

        Car entity = mapper.map(dto, Car.class);

        assertThat(entity).isNotNull();
        assertThat(entity.getMake()).isEqualTo("M1");
        assertThat(entity.getCarType()).isEqualTo("OTHER");

    }


    @ComponentScan("io.github.zhaord.mapstruct.plus")
    @Configuration
    @Component
    static class AutoMapTestConfiguration {


    }


}

```

## Wiki
* `@Mapping` Annotation attributes
  `@AutoMapField` inherits from the annotation of `@Mapping`, all the attributes of `@Mapping` can be used in `@AutoMapField`
