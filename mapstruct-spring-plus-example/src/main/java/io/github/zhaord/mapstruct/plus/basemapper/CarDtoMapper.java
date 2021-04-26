package io.github.zhaord.mapstruct.plus.basemapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

@Mapper(componentModel = "spring")
public interface CarDtoMapper{
    Car dtoToEntity(CarDto dto);

    @InheritInverseConfiguration(name = "dtoToEntity")
    CarDto entityToDto(Car dto);

    Car dtoMapToEntity(CarDto dto, @MappingTarget Car car);

    CarDto entityMapToDto(Car dto, @MappingTarget CarDto car);
}
