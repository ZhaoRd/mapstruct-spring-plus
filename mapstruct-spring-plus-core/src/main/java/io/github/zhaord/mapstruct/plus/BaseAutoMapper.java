package io.github.zhaord.mapstruct.plus;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MappingTarget;

public interface BaseAutoMapper<S,T>{

    T map(S source);
    T mapTarget(S source, @MappingTarget T target);

    @InheritInverseConfiguration(name = "map")
    S reverseMap(T source);

    @InheritInverseConfiguration(name = "mapTarget")
    S reverseMapTarget(T source, @MappingTarget S target);


}
