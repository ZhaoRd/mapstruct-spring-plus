package io.github.zhaord.mapstruct.plus;

import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import io.github.zhaord.mapstruct.plus.annotations.AutoMapField;
import lombok.Data;

@Data
@AutoMap(targetType = SeatConfiguration.class)
public class SeatConfigurationDto {
    @AutoMapField(target = "numberOfSeats")
    private int seatCount;
    @AutoMapField(target = "seatMaterial")
    private String material;
}
