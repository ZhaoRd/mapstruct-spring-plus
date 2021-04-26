package io.github.zhaord.mapstruct.plus.basemap;


import io.github.zhaord.mapstruct.plus.MapStructPlusTestConfiguration;
import io.github.zhaord.mapstruct.plus.basemapper.Car;
import io.github.zhaord.mapstruct.plus.basemapper.CarDto;
import io.github.zhaord.mapstruct.plus.basemapper.CarDtoMapper;
import lombok.Data;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {MapStructPlusTestConfiguration.class})
public class BaseMapTest {

    @Autowired
    private CarDtoMapper mapper;

    @Test
    public void test(){
        CarDto dto = new CarDto();
        dto.setName("123");

        Car entity = this.mapper.dtoToEntity(dto);
        assertThat(entity.getName()).isEqualTo("123");
    }

}
