package com.frodgim.tickets.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CapacityDTO {
    @NotNull
    private Integer maxCapacity;
}
