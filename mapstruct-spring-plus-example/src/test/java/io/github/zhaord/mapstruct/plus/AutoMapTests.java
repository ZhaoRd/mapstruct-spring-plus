package io.github.zhaord.mapstruct.plus;

import lombok.var;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {MapStructPlusTestConfiguration.class})
public class AutoMapTests {

    @Autowired
    private IObjectMapper mapper;

    @Test
    public void testDtoToEntity() {

        var dto = new CarDto();
        dto.setMake("M1");
        dto.setType("OTHER");

        var seatDto = new SeatConfigurationDto();
        seatDto.setSeatCount(100);
        seatDto.setMaterial("LEATHER");

        dto.setSeats(seatDto);

        Car entity = mapper.map(dto, Car.class);

        assertThat(entity).isNotNull();
        assertThat(entity.getMake()).isEqualTo("M1");
        assertThat(entity.getType()).isEqualTo(CarType.OTHER);

        assertThat(entity.getSeatConfiguration()).isNotNull();
        assertThat(entity.getSeatConfiguration().getNumberOfSeats()).isEqualTo(100);
        assertThat(entity.getSeatConfiguration().getSeatMaterial()).isEqualTo(SeatMaterial.LEATHER);


    }

}
