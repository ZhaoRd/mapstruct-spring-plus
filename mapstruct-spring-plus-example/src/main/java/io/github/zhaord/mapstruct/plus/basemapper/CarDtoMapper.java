package io.github.zhaord.mapstruct.plus.basemapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarDtoMapper{
    Car dtoToEntity(CarDto dto);
}
