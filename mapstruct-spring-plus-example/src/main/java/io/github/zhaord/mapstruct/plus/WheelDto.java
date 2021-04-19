package io.github.zhaord.mapstruct.plus;

import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import lombok.Data;

@Data
@AutoMap(targetType = Wheel.class)
public class WheelDto {
    private String position;
    private int diameter;
}
