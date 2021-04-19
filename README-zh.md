# mapstruct-spring-plus
是mapstruct和spring的增强包，简化mapstruct结合spring的使用方式，实现方式参考了mapstruct-spring-extensions项目

创建Dto和Entity
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

## 使用 MapStruct Spring Plus

### Maven
对于maven项目，使用如下的POM文件

```xml

...

<properties>
    <org.mapstruct.version>1.4.2.Final</org.mapstruct.version>
    <io.github.zhaord.version>1.0.0.Release</io.github.zhaord.version>
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
对于Gradle项目，使用如下配置

``` gradle
dependencies {
    ...
    compile 'org.mapstruct:mapstruct:1.4.2.Final'
    compile 'io.github.zhaord:mapstruct-spring-plus-boot-starter:1.0.0.Release'

    annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
    testAnnotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final' // if you are using mapstruct in test code

    annotationProcessor 'io.github.zhaord:mapstruct-spring-plus-processor:1.0.0.Release'
    testAnnotationProcessor 'io.github.zhaord:mapstruct-spring-plus-processor:1.0.0.Release' // if you are using mapstruct in test code
    ...
}
```

### 使用案例
sprign中注入 `IObjectMapper`, 调用 `objectMapper.map(dto, Car.class)`

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

## 问题
* `@Mapping` 注解属性
  `@AutoMapField` 继承自`@Mapping` 注解，素有`@Mapping`属性都可以在`@AutoMapField`中使用