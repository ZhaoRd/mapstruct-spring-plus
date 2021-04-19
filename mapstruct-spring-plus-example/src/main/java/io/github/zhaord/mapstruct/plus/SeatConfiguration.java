package io.github.zhaord.mapstruct.plus;

import lombok.Data;

@Data
public class SeatConfiguration {
    private int numberOfSeats;
    private SeatMaterial seatMaterial;

    private String name;
}
