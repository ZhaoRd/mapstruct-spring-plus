package io.github.zhaord.mapstruct.plus;

import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import io.github.zhaord.mapstruct.plus.annotations.AutoMapField;
import lombok.Data;

@Data
@AutoMap(targetType = Car.class)
public class CarDto {
    private String make;

    @AutoMapField(target = "seatConfiguration")
    private SeatConfigurationDto seats;
    private String type;

    @AutoMapField(target = "seatConfiguration")
    private String fromSeatName;

}
