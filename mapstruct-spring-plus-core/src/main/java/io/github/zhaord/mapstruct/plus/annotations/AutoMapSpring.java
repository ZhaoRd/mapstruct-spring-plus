package io.github.zhaord.mapstruct.plus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface AutoMapSpring {

    String autoMapSpringAdapterPackage() default "";

    String autoMapSpringAdapterClassName() default "AutoMapMapperAdapter";

    String autoMapSpringBeanName() default "";
}
