package io.github.zhaord.mapstruct.plus;

import lombok.Data;

@Data
public class Car {
    private String make;
    private SeatConfiguration seatConfiguration;
    private CarType type;
}
